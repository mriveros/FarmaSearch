var map ;
var marker;
	$( document ).ready(function() {
		enviarFormularioAjax();
		if(document.getElementById("map-canvas"))
			initialize();
	});  
	///FUNCIONES PARA GOOGLE MAPS
	function initialize() {
		var mapOptions = {
			center: new google.maps.LatLng(37.1621612, -3.5965569),
			zoom: 15,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			panControl: true,
			zoomControl: true,
			mapTypeControl: true,
			scaleControl: true,
			streetViewControl: true,
			overviewMapControl: true,
			rotateControl: true,
		};
	
		map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
		google.maps.event.addListener(map, 'click', function(event) {
			placeMarker(event.latLng.lat(),event.latLng.lng());
			});
	}

	function placeMarker(latitud,longitud) {
		var location = new google.maps.LatLng(parseFloat(latitud),parseFloat(longitud));
		 var image = 'img/farmacialogo.png';
		if(marker){
			marker.setPosition(location);
		}else{
			
		  marker = new google.maps.Marker({
			position: location,
			map: map,
			draggable: true,
			icon:image
		  });
		}
		  marker.addListener('dragend',function(event) {
			  $("#latitud").val(event.latLng.lat());
			  $("#longitud").val(event.latLng.lng());
			});
		  establecerLatLng(location.lat(),location.lng());
	}
	function establecerLatLng(lat,lng){
		$("#latitud").val(lat);
		$("#longitud").val(lng);
	}
	$(window).resize(function () {
			  var h = $(window).height(),
				offsetTop = 105; // Calculate the top offset
			
			  $('#map-canvas').css('height', (h - offsetTop));
	}).resize();
	///FUNCIONES PARA GOOGLE MAPS
  

 /* $("#file-0").fileinput({
        'allowedFileExtensions' : ['jpg', 'png','gif'],
  });*/

  $("#imagen").fileinput({
        uploadUrl: 'localhost', // you must set a valid URL here else you will get an error
        allowedFileExtensions : ['jpg', 'png','gif'],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
  });

   ///Funciones para enviar los datos del formulario a servlet en formato JSON        
  function ConvertFormToJSON(form){
	    var array = jQuery(form).serializeArray();
	    var json = {};
	    
	    /*jQuery.each(array, function() {
	        json[this.name] = this.value || '';
	    });*/
	    var o = {};
        var a = jQuery(form).serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
	    
	   // return json;
	}         
  
  function enviarFormularioAjax(){
	 $('form').submit(function (e) {
		        e.preventDefault();
		        
		        //var form = this;
		       // var json = ConvertFormToJSON(form);
		        var formdata = new FormData(this);
		        
		        var xhr = new XMLHttpRequest();       

		        xhr.open("POST",$('form').attr("action"), true);

		        xhr.send(formdata);

		        xhr.onload = function(e) {

		            if (this.status == 200) {
		            	alert("El registro ha sido añadido/editado");
		            	$(':input','form')
						  .not(':button, :submit, :reset, :hidden')
						  .val('')
						  .removeAttr('checked')
						  .removeAttr('selected');
						if(marker){
							marker.setMap(null);
							marker=null;
						}
						//location.reload(true); Doesn't work to IE neither Firefox;
					    //also, hash tags mush be removed or no postback will occur.
					    window.location.href = window.location.href.replace(/\?id=[0-9]*$/, '');
		            }

		        };                    
	
		       /* $.ajax({
		            type: "POST",
		            url: $('form').attr("action"),
		            async:false,
		            data: JSON.stringify(json),
		            success: function(response){
		            	alert(response);
						$(':input','form')
						  .not(':button, :submit, :reset, :hidden')
						  .val('')
						  .removeAttr('checked')
						  .removeAttr('selected');
						if(marker){
							marker.setMap(null);
							marker=null;
						}
		            },
			        error: function() {
		                alert('fail');
		            }
		        });
	
		        return true;*/
		}); 
  }
   ///Funciones para enviar los datos del formulario a servlet en formato JSON
