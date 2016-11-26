var TcRegion = {
    findProvinces: function (render) {
        $.ajax({
            url: __base + '/regions/find_provinces.json',
            type: 'get',
            success: function (data, textStatus, jqXHR) {
                __doWithTcR(data, function (body) {
                    render(body);
                });
            }
        });
    },

    findCities: function (province_code, render) {
        $.ajax({
            url: __base + '/regions/find_cities/{code}.json'.replace('{code}', province_code),
            type: 'get',
            success: function (data, textStatus, jqXHR) {
                __doWithTcR(data, function (body) {
                    render(body);
                });
            }
        });
    },

    findDistricts: function (city_code, render) {
        $.ajax({
            url: __base + '/regions/find_districts/{code}.json'.replace('{code}', city_code),
            type: 'get',
            success: function (data, textStatus, jqXHR) {
                __doWithTcR(data, function (body) {
                    render(body);
                });
            }
        });
    },

    defaultInitProvince: function (_form, $province_select) {
        TcRegion.findProvinces(function(provinces) {
            $province_select.empty();
            $province_select.append('<option value="">请选择一个省</option>');
            for (var i = 0; i < provinces.length; i++) {
                var province = provinces[i];
                $province_select.append('<option value="' + province.code + '">' + province.name + '</option>');
            }
            _form.render('select');
        });
    },

    defaultInitCity: function (_form, province_code, $city_select, $district_select) {
        TcRegion.findCities(province_code, function(cities) {
            $city_select.empty();
            $district_select.empty();
            $city_select.append('<option value="">请选择一个市</option>');
            $district_select.append('<option value="">请选择一个区</option>');
            for (var i = 0; i < cities.length; i++) {
                var city = cities[i];
                $city_select.append('<option value="' + city.code + '">' + city.name + '</option>');
            }
            _form.render('select');
        });
    },

    defaultInitDistrict: function (_form, city_code, $district_select) {
        TcRegion.findDistricts(city_code, function(districts) {
            $district_select.empty();
            $district_select.append('<option value="">请选择一个区</option>');
            for (var i = 0; i < districts.length; i++) {
                var district = districts[i];
                $district_select.append('<option value="' + district.code + '">' + district.name + '</option>');
            }
            _form.render('select');
        });
    }
};