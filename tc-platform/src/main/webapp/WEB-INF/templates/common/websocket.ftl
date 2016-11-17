<div id="panel"></div>
<script src="${.vars.staticBase}/libs/jquery-3.1.0.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/websocket/sockjs.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/websocket/stomp.min.js" type="application/javascript"></script>

<script type="text/javascript">

    $(document).ajaxError(function () {
        console.error('ajax error');
    });


    // websocket
    var client = Stomp.over(new SockJS('http://websocket.tc.com/endpoint'));
    client.connect({}, function (frame) {
        console.log('connected ...');

        client.subscribe('/topic/greetings', function(greeting){
            console.log(greeting);
        });
    });

    function run() {
        client.send('/app/handle', {}, 'helo');
    }


</script>