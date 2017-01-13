local resty_upload = require "resty.upload"
local resty_random = require "resty.random"
local resty_string = require "resty.string"
local cjson = require "cjson"

local trunk_size = 4096
local fs_folder = "/Users/zhangyaowu/env/data/upload"
local http_base = "http://static.tc.com/resources"

-- header: ["Content-Disposition","form-data; name=\"file1\"; filename=\"a.txt\""]
-- header: ["Content-Type","text\/plain"]
-- body: xxx
-- part_end: none
-- header
-- header
-- body
-- part_end
-- eof

-- init
local form, err = resty_upload:new(trunk_size)

if not form then
    ngx.log(ngx.ERR, "failed to new upload", err)
    local response = {}
    response["code"] = 500
    response["description"] = "服务器异常，请稍后再试！"
    ngx.say(cjson.encode(response))
end

local rs = {}
local file
local fieldname
local fileextension
local sub_dist
while true do
    -- read
    local typ, res, err = form:read()
    if not typ then
        ngx.log(ngx.ERR, "failed to find upload type", err)
        local response = {}
        response["code"] = 500
        response["description"] = "服务器异常，请稍后再试！"
        ngx.say(cjson.encode(response))
        return
    end

    if typ == "header" then
        -- generate filename
        local filename = resty_string.to_hex(resty_random.bytes(24))
        if not filename then
            local response = {}
            response["code"] = 500
            response["description"] = "服务器异常，请稍后再试！"
            ngx.say(cjson.encode(response))
            return
        end
        -- new file
        file = io.open(fs_folder .. "/" .. dist .. "/" .. filename .. file_extension, "w+")
        if not file then
            local response = {}
            response["code"] = 500
            response["description"] = "服务器异常，请稍后再试！"
            ngx.say(cjson.encode(response))
            return
        end
        rs[field_name] = http_base .. "/" .. field_name .. file_extension
    end

    if typ == "body" then
        -- save file
        if file ~= nil then
            file:write(res)
        end
    end

    if typ == "part_end" then
        file:close()
        file = nil
    end

    if typ == "eof" then
        break
    end
end

ngx.say(cjson.encode(rs))