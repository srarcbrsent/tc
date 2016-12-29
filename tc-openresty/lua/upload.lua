-- import
local resty_upload = require "resty.upload"
local resty_uuid = require "resty.uuid"
local cjson = require "cjson"

-- const
local destnation_dir = "/Users/zhangyaowu/env/data/upload"
local destnation_url = 'http://resource.tc.com'
local chunk_size = 4096

-- global vars
local form = resty_upload:new(chunk_size)

-- 构建模块(请求头携带模块名)
function get_module_name(header)
    local types = ngx.re.match(header, '(.+)filetype="(.+)"(.*)')
    if types then
        local type = types[2]
        if type == 'img' then
            return 'img'
        elseif type == 'excel' then
            return 'excel'
        end
    end
    return 'o'
end

-- 获取文件名
function get_filename(header)
    local filename = ngx.re.match(header, '(.+)filename="(.+)"(.*)')
    if filename then
        return filename[2]
    end
end

-- 获取文件扩展名
function get_extension_name(str)
    return str:match(".+%.(%w+)$")
end

-- 获取全路径
function get_filepath(base, module, name, extension)
    return base .. "/" .. module .. "/" .. name .. "." .. extension
end

-- 构建返回值
function get_response(code, message, body)
    local tc_r
    tc_r[code] = code
    tc_r[message] = message
    if not body then
        tc_r[body] = body
    end
end

-- 处理上传
function handle_uploading()
    local file
    local file_module
    local file_uid = resty_uuid.new()
    local file_extension
    while true do
        local typ, res, err = form:read()

        if not typ then
            ngx.say(cjson.encode(get_response(8888, tostring(err), nil)))
            return
        end

        if typ == "header" then
            file_module = get_module_name(res)
            file_extension = get_extension_name(res)
            if not file_extension or not file_module then
                ngx.say(cjson.encode(get_response(8888, tostring(err), nil)))
                return
            end
            -- base/module/fileuid.extension
            file = io.open(get_filepath(destnation_dir, file_module, file_uid, file_extension), "w+")
            if not file then
                ngx.say(cjson.encode(get_response(9999, tostring(err), nil)))
                return
            end
        elseif typ == "body" then
            if file then
                file:write(res)
            end
        elseif typ == "part_end" then
            if file then
                file:close()
            end
            file = nil
        elseif typ == "eof" then
            ngx.say(cjson.encode(get_response(0, '上传成功',
                get_filepath(destnation_url, file_module, file_uid, file_extension))))
            return
        else
            -- do nothing
        end
    end
end

handle_uploading();