/**
 * 元数据页面scripts
 * @author mazc
 */
(function($) {
	$(document).ready(function(){
		$('.btn-additem').on("click", function(){
			var name = $("#newitemname").val();
			if(!name){
				alert("请输入元数据子项名称!");
				return false;
			}
			var value = $("#newitemvalue").val();
			if(!value){
                alert("请输入元数据子项编码!");
                return false;
			}
			var wrapper = $("#itemWrapper");
			var newRow = 
			"<div class='alert alert-info'><button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button>"+name;
			if(value){
				newRow += " <small>("+value+")</small>";
			}
			else{
				value = "";				
			}
			var jsonStr = JSON.stringify({id: 0, itemName: name, itemValue: value});
			newRow += "<input type='hidden' name='items' value='" + jsonStr + "' /></div>";
			wrapper.append(newRow);
			
			$("#newitemname").val("");
            $("#newitemvalue").val("");
		});
	});
})(jQuery);