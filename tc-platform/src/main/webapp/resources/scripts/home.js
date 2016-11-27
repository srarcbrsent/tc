layui.use(['form', 'upload'],
    function () {
        // const
        var _form = layui.form();

        // function
        var validRegion = function () {
            var $province = $('select[name = province]');
            var province = $province.val();
            var $city = $('select[name = city]');
            var city = $city.val();
            var $district = $('select[name = district]');
            var district = $district.val();
            var region = province + '-' + city + '-' + district;
            var $region_selects = $province.add($city).add($district);
            // pretty hack, waiting layui generate new dom.
            if (!new RegExp('^P[0-9]{2}-C[0-9]{3}-D[0-9]{4}$').test(region)) {
                setTimeout(function () {
                    $region_selects
                        .parent()
                        .children('div')
                        .find('input[type = text]')
                        .removeClass('jv_valid')
                        .addClass('jv_error');
                }, 20);
                return false;
            } else {
                setTimeout(function () {
                    $region_selects
                        .parent()
                        .children('div')
                        .find('input[type = text]')
                        .addClass('jv_valid')
                        .removeClass('jv_error');
                }, 20);
                return true;
            }
        };

        // jquery binding
        var $signin_form = $("#signin_form");
        var validator = $signin_form.validate({
            debug: true,

            ignore: '.layui-unselect',

            validClass: 'jv_valid',

            success: function (element) {
            },

            errorClass: 'jv_error',

            errorElement: '_',

            errorPlacement: function (error, element) {
            },

            rules: {
                name: {
                    required: true,
                    pattern: '^[a-zA-Z0-9_\\u4E00-\\u9FA5]{6,16}$'
                },
                account: {
                    required: true,
                    pattern: '^[a-zA-Z0-9_]{6,16}$'
                },
                email: {
                    required: false,
                    email: true
                },
                mobile: {
                    required: false,
                    pattern: '^1[34578]\\d{9}$'
                },
                password: {
                    required: true,
                    pattern: '^[a-zA-Z0-9_]{6,16}$'
                }
            },

            messages: {},

            submitHandler: function (form) {
                var $province = $('select[name = province]');
                var province = $province.val();
                var $city = $('select[name = city]');
                var city = $city.val();
                var $district = $('select[name = district]');
                var district = $district.val();
                var region = province + '-' + city + '-' + district;
                $('input[type = hidden][name = region]').val(region);
                return false;
            }
        });

        // form submit event
        $signin_form.submit(function () {
            return validRegion();
        });
        
        // form reset event
        $signin_form.find('button[type = reset]').click(function () {
            validator.resetForm();
        });

        // layui binding
        // bind initial province.
        var $province_select = $('select[name = province]');
        TcRegion.defaultInitProvince(_form, $province_select);

        // bind province selection.
        _form.on('select(signin_province)', function (data) {
            var province_code = data.value;
            var $city_select = $("select[name = city]");
            var $district_select = $("select[name = district]");
            TcRegion.defaultInitCity(_form, province_code, $city_select, $district_select);
        });

        // bind city selection.
        _form.on('select(signin_city)', function (data) {
            var city_code = data.value;
            var $district_select = $("select[name = district]");
            TcRegion.defaultInitDistrict(_form, city_code, $district_select);
        });

        _form.on('select(signin_district)', function (data) {
            validRegion();
        });
    }
);