var position, amount, price, product, productId,
    numberString, positionString;
        
function init(){
    position = $('#position'),
    amount = $('#amount'),
    price = $('#price'),
    product = $('#product'),
    productId = $('#productId'),
    numberString = "Значение должно быть положительным числом!",
    positionString = "Выберите продукт!";
}

function validate(){
    if (checkField( position, isNumber, numberString )){
        if (checkField( amount, isNumber, numberString )) {
            if (checkField( price, isNumber, numberString )){
                if (checkField( product, checkProductId, positionString )){
                    return true;
                }
            }
        }
    }
    return false;
}

function isNumber(element){
   return (/^\d+\.?\d*$/).test(element.val());
}

function checkProductId(){
    return isNumber(productId);
}

function clear(){
    $(".control-group").removeClass("error");
    $(".help-inline").text("");
}

function checkField(element, checker, message){
    if (checker(element)) {
        return true;
    } else {
        clear();
        error(element, message);
        return false;
    }
}

function error(element, message) {
    var help = element.siblings(".help-inline")
    help.text(message);
    element.parent().parent().addClass("error");
    element.focus();
}

$(function() {
    init();
    
    var product = $('#product'),
    planName = $('#name'),
    cache = {};

    $( "#product" ).autocomplete({
      minLength: 2,
      source: function( request, response ) {
        var name = request.term;
        if ( name in cache ) {
          response( cache[ name ] );
          return;
        }
        var param = {
            	    "name"  : name,
                    "count" : 8
        };
 
        $.getJSON( "main?action=production.find", param, function( data, status, xhr ) {
          cache[ name ] = data;
          response( data );
        });
      },
      focus: function( event, ui ) {
        product.val( ui.item.name );
        return false;
      },
      select: function( event, ui ) {
        product.val( ui.item.name );
        productId.val( ui.item.id );
        return false;
      }
    })
    .data( "ui-autocomplete" )._renderItem = function( ul, item ) {
      return $( "<li>" )
        .append( "<a>" + item.code + " " + item.name + "</a>" )
        .appendTo( ul );
    };
    
    $(".copy_image img").click(function() {
      planName.val(product.val());
    })
});