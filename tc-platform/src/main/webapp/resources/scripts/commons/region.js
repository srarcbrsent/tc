// regions
function _regionsVue(el, data) {
    let _regionsVue = new Vue({
        el: el,
        data: data,
        mounted: function () {
            let $regions = $(el);
            // bind province
            $regions.find('> select:nth-child(1)').on('change', function () {
                _regionsVue.currProvince = $(this).val();
            });

            // bind city
            $regions.find('> select:nth-child(2)').on('change', function () {
                _regionsVue.currCity = $(this).val();
            });

            // bind city
            $regions.find('> select:nth-child(3)').on('change', function () {
                _regionsVue.currDistrict = $(this).val();
            });

            // init province
            axios
                .get(__base + '/regions/find_provinces.json', __axiosConfig)
                .then(function (response) {
                    __doWithTcR(response.data, function (body) {
                        _regionsVue.provinces = body;
                    });
                })
                .catch(function () {
                    layer.alert('抱歉，系统异常，请稍后再试！');
                });
        },
        watch: {
            // let cities and districts empty first, otherwise vue will optimize dom
            // render, only change the text and not reset the selected one.
            currProvince: function (province) {
                _regionsVue.cities = [];
                axios
                    .get(__base + '/regions/find_cities/{code}.json'.replace('{code}', province), __axiosConfig)
                    .then(function (response) {
                        __doWithTcR(response.data, function (body) {
                            _regionsVue.cities = body;
                        });
                    })
                    .catch(function () {
                        layer.alert('抱歉，系统异常，请稍后再试！');
                    });
            },

            currCity: function (city) {
                _regionsVue.districts = [];
                if (_.isUndefined(city) || city == '') {
                    return;
                }
                axios
                    .get(__base + '/regions/find_districts/{code}.json'.replace('{code}', city), __axiosConfig)
                    .then(function (response) {
                        __doWithTcR(response.data, function (body) {
                            _regionsVue.districts = body;
                        });
                    })
                    .catch(function () {
                        layer.alert('抱歉，系统异常，请稍后再试！');
                    });
            }
        }
    });
    return _regionsVue;
}
