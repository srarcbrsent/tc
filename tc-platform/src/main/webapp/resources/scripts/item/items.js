$(document).ready(function () {

    // brand div radio
    $('#filterbox_brand_div').find('> button').click(function () {
        if ($(this).hasClass('btn-primary')) {
            $(this).removeClass('btn-primary');
        } else {
            $(this).siblings().removeClass('btn-primary');
            $(this).addClass('btn-primary');
        }
    });

    // category div checkbox
    $('#filterbox_category_div').find('> button').click(function () {
        if ($(this).hasClass('btn-primary')) {
            $(this).removeClass('btn-primary');
        } else {
            $(this).addClass('btn-primary');
        }
    });

    // sort by price
    $('#filterbox_sort_price').click(function () {
        if ($(this).hasClass('btn-primary')) {
            $(this).removeClass('btn-primary');
            $(this).html('<i class="icon icon-double-angle-down"></i> 价格降序');
        } else {
            $(this).addClass('btn-primary');
            $(this).html('<i class="icon icon-double-angle-up"></i> 价格升序');
        }
    });

    // sort by name
    $('#filterbox_sort_name').click(function () {
        if ($(this).hasClass('btn-primary')) {
            $(this).removeClass('btn-primary');
            $(this).html('<i class="icon icon-double-angle-down"></i> 名称降序');
        } else {
            $(this).addClass('btn-primary');
            $(this).html('<i class="icon icon-double-angle-up"></i> 名称升序');
        }
    });

    // filterbox_only_in_stock
    $('#filterbox_only_in_stock').click(function () {
        if ($(this).hasClass('btn-primary')) {
            $(this).removeClass('btn-primary');
            $(this).html('<i class="icon icon-filter"></i> 全部显示');
        } else {
            $(this).addClass('btn-primary');
            $(this).html('<i class="icon icon-filter"></i> 显示有货');
        }
    });

    // filterbox_reset
    $('#filterbox_reset').click(function () {
        $('#filterbox_div').find('button').removeClass('btn-primary');
    });

    // typeahead
    $.typeahead({
        input: '.js-typeahead-country_v1',
        order: "desc",
        source: {
            data: [
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda",
                "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
                "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
                "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burma",
                "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Republic", "Chad",
                "Chile", "China", "Colombia", "Comoros", "Congo, Democratic Republic", "Congo, Republic of the",
                "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti",
                "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador",
                "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon",
                "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Greenland", "Grenada", "Guatemala", "Guinea",
                "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",
                "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
                "Kazakhstan", "Kenya", "Kiribati", "Korea, North", "Korea, South", "Kuwait", "Kyrgyzstan", "Laos",
                "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
                "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
                "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Mongolia", "Morocco", "Monaco",
                "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger",
                "Nigeria", "Norway", "Oman", "Pakistan", "Panama", "Papua New Guinea", "Paraguay", "Peru",
                "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Samoa", "San Marino",
                "Sao Tome", "Saudi Arabia", "Senegal", "Serbia and Montenegro", "Seychelles", "Sierra Leone",
                "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "Spain",
                "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan",
                "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
                "Turkmenistan", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
                "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
            ]
        },
        callback: {
            onInit: function (node) {
                console.log('Typeahead Initiated on ' + node.selector);
            }
        }
    });

});