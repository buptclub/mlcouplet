// Global Variable
var Couplet = (function() {

    var inputSelector = '#inputFirstCouplet';

    // ===========================
    // Couplet MODULE
    // ===========================

    function initEvent() {
        $( inputSelector )
            .on( 'keyup', function( e ) {
                if (e.keyCode === 13) {
                    _generateCoupletAndLossFocus();
                }
            });

        $( '.button-red' )
            .on( 'click', function( e ) {
                _generateCoupletAndLossFocus();
            });

        $( inputSelector ).focus();
    }

    function _generateCoupletAndLossFocus() {
        generateCouplet();
        $( inputSelector ).blur();
    }

    function buildCoupletHtml( singleCouplet ) {
        var html = '';

        for (var i=0; i<singleCouplet.length; i++) {

            html += '<tr>';
            html += '  <td style="text-align: center; width: 57px; height: 57px;">'
            html += '    <input type="text" maxlength="1" readonly="" value="' + singleCouplet[ i ] + '">'
            html += '  </td>';
            html += '</tr>';
        }
        
        return html;
    }

    function fillCouplet( firstCouplet, secondCouplet ) {
        $( '#firstCouplet table tbody' ).html( buildCoupletHtml ( firstCouplet ) );
        $( '#secondCouplet table tbody' ).html( buildCoupletHtml ( secondCouplet ) );
    }
    
    function generateCouplet( firstCouplet /** optional **/ ) {
        progressBar.show();
        
        firstCouplet = firstCouplet || $( inputSelector ).val();
        api.generate( firstCouplet, function (status, response) {

            if (status == 1) {
                alert( '请输入上联' );
            } else if (status == 2) {
                alert( '错误: 与服务器断开连接' );
            } else if (status == 0) {
                if ( response.status != 0 ) {
                    alert( '错误: ' + response.exception.message );
                }

                if ( response.data ) {
                    var secondCoupletList = response.data.secondCoupletList,
                        firstCouplet = response.data.firstCouplet;
                    
                    if ( firstCouplet &&
                         secondCoupletList ) {
                        fillCouplet( firstCouplet, secondCoupletList[ Math.floor(Math.random() * secondCoupletList.length) ].couplet );
                    }
                }
            }

            progressBar.hide();
            
        } );
    }

    // ===========================
    // PROGRESS BAR MODULE
    // ===========================

    var progressBar = (function() {

        function show() {
            $( '.spinner' ).fadeIn();
        }

        function hide() {
            $( '.spinner' ).fadeOut();
        }
        
        return {
            show: show,
            hide: hide
        }
        
    }());

    // ===========================
    // API MODULE
    // ===========================
    var api = (function() {

        function generate(firstCouplet, callback) {
            if ( !firstCouplet ) {
                callback( 1, 'no input' ); // no input
                return;
            }

            // request url
            $.ajax({
                url: '/generate/' + firstCouplet,
                contentType: 'json'
            }).done(function( data, textStatus, jqXHR ) {
                callback( 0, data );
            }).fail(function( jqXHR, textStatus, errorThrown ) {
                callback( 2, errorThrown);
            });
        }

        return {
            generate: generate
        }
    }());

    // Init Event
    initEvent();
    
    // Global API
    return {
        api: api,
        progressBar: progressBar,
        generateCouplet: generateCouplet
    }
    
}())
