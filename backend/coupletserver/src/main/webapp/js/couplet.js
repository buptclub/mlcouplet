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
        firstCouplet = firstCouplet || $( inputSelector ).val();
        api.generate( firstCouplet, function (status, response) {

            if (status == 1) {
                alert('请输入上联');
                return;
            } else if (status == 2) {
                alert('错误', response);
            } else if (status == 0) {
                console.log( '上联:' + response.firstCouplet );
                console.log( '下联:' + response.secondCouplet );

                if ( response.firstCouplet &&
                     response.secondCouplet ) {
                    fillCouplet( response.firstCouplet, response.secondCouplet );  
                }
            }
            
        } );
    }

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
                contentType: 'json',
                success: function ( response ) {
                    
                },
                error: function ( exception ) {
                    
                }
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
        generateCouplet: generateCouplet
    }
    
}())
