/**
 * 基础扩展JS 对js或jq原生对象进行扩展，以及一些通用方法。
 * 
 */

var smartUtil = smartUtil || $.extend({}, smartUtil);/* 定义全局对象，类似于命名空间或包的作用 */

/**
 * 改变jQuery的AJAX默认属性和方法
 * 
 * @requires jQuery
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText);
	}
});

/**
 * 获得项目根路径 使用方法：smartUtil.bp();
 * 
 * @returns 项目根路径
 */
smartUtil.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};



/**
 * 生成UUID
 * 
 * @returns UUID字符串
 */
smartUtil.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};

/**
 * 获得URL参数
 * 
 * @returns 对应名称的值
 */
smartUtil.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @returns list
 */
smartUtil.getList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * 判断浏览器是否是IE并且版本小于8
 * 
 * @requires jQuery 
 * @returns true/false
 */
smartUtil.isLessThanIe8 = function() {
	return ($.browser.msie && $.browser.version < 8);
};



/**
 * 将JSON对象转换成字符串
 * 
 * @param o
 * @returns string
 */
smartUtil.jsonToString = function(o) {
	var r = [];
	if (typeof o == "string")
		return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + obj2str(o[i]));
			if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for ( var i = 0; i < o.length; i++)
				r.push(obj2str(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
};

/**
 * 格式化日期时间
 * 
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
	if (isNaN(this.getMonth())) {
		return '';
	}
	if (!format) {
		format = "yyyy-MM-dd hh:mm:ss";
	}
	var o = {
		/* month */
		"M+" : this.getMonth() + 1,
		/* day */
		"d+" : this.getDate(),
		/* hour */
		"h+" : this.getHours(),
		/* minute */
		"m+" : this.getMinutes(),
		/* second */
		"s+" : this.getSeconds(),
		/* quarter */
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		/* millisecond */
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/** 
  *pattern 为格式化参数 
 */
function getFormatDate(date, pattern) {
	if(!date) return '';
date = new Date(date).format(pattern);
return date;
}

var share = {
	/**
	 * 跨框架数据共享接口
	 * 
	 * @param {String}
	 *            存储的数据名
	 * @param {Any}
	 *            将要存储的任意数据(无此项则返回被查询的数据)
	 */
	data: function (name, value) {
		var top = window.top,
			cache = top['_CACHE'] || {};
		top['_CACHE'] = cache;

		return value !== undefined ? cache[name] = value : cache[name];
	},
	/**
	 * 数据共享删除接口
	 * 
	 * @param {String}
	 *            删除的数据名
	 */
	removeData: function (name) {
		var cache = window.top['_CACHE'];
		if (cache && cache[name]) delete cache[name];
	}
};

/**
 * 超出范围的字符换成省略号
 * 
 * @param str
 * @param sub_length
 * @returns
 */
smartUtil.suolve = function(str,sub_length){   
	var end='';
	try{
    var temp1 = str.replace(/[^\x00-\xff]/g,"**");// 精髓
    var temp2 = temp1.substring(0,sub_length);   
    // 找出有多少个*
    var x_length = temp2.split("\*").length - 1 ;   
    var hanzi_num = x_length /2 ;   
    sub_length = sub_length - hanzi_num ;// 实际需要sub的长度是总长度-汉字长度
    var res = str.substring(0,sub_length);   
    if(sub_length < str.length ){   
        end  =res+"..." ;   
    }else{    
        end  = res ;   
    }   
	}catch(e){}
    return end ;   
}; 

function showMessage(json,obj){
	if(obj){
		obj.smartUtil.messagerShow({
			height:130,
			width:300,
			timeout:9000,
			msg : json.msg,
			title : '提示'
		});
	}else if(opener){  // 如果是open 出来的
		opener.window.top.smartUtil.messagerShow({
			height:130,
			width:300,
			timeout:9000,
			msg : json.msg,
			title : '提示'
		});
	}else{
		parent.smartUtil.messagerShow({
			height:130,
			width:300,
			timeout:9000,
			msg : json.msg,
			title : '提示'
		});
	}
}

function openModalDialog(src, width, height, showScroll){   
	return window.showModalDialog (src,window,"location:No;status:No;help:No;dialogWidth:"+width+";dialogHeight:"+height+";scroll:"+showScroll+";");   
}

function openWin(src,name,width, height, showScroll){
	// gotoUrl(src);
	var iTop=(window.screen.height-height)/2; 
	var iLeft=(window.screen.width-width)/2; 
	if(name||!''==name){
	}else{
		name = parseInt(Math.random()*38);
	}
	if(external.menuArguments==undefined) {
		return window.open (src,name,'height='+height+',width='+width+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,resizable=no,location=no,status=no,scrollbars='+showScroll);  
	}else {
		return external.menuArguments.open (src,name,'height='+height+'px,width='+width+'px,top='+iTop+'px,left='+iLeft+'px,toolbar=no,menubar=no,scrollbars='+showScroll+', resizable=no,location=no, status=no') ;  
	}
}

function refreshParentPage(url){ 
	if(window.opener){ 
		if(url){ 
			window.opener.location.href = url; 
		}else{ 
			try{ 
				window.opener.refreshSelfPage(); 
			}catch(e){ 
				try{ 
					var tempUrl = window.opener.location.href; 
					if(tempUrl.indexOf("?")>0){ 
						window.opener.location.href = tempUrl+"&rnd="+Math.random(); 
					}else{ 
						window.opener.location.href = tempUrl+"?rnd="+Math.random(); 
					} 
				}catch(e){ 
				} 
			}   
		} 
	} 
} 
/**
 * 判断是否为空
 * @param str
 * @returns
 */
function isNull(str){
	if ( str == "" || str == null || str == "null" ){
		return true;
	}
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}

/**
 * 获取当前时间的前几天 或后几天
 * @param AddDayCount -1（前一天） 2（后两天） 0（今天）
 * @returns {String}
 */
function getDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;//获取当前月份的日期
    var d = dd.getDate();
    return y+"-"+m+"-"+d;
}


//保留两位小数  需要确定是数字类型再调用此方法
function toFixed(value) {
	return value.toFixed(2);
}

/**
 * 数字格式转换成千分位
 * 
 * @param{Object}num
 */
function commafy(num){
	 if((num+"").replace(/(\s*$)/g, "")==""){
	      return "";
	   }
	 if(isNaN(num)){
	      return "";
	 }
	 num = toDecimal2(num);
	 num = num+"";
	 if(/^.*\..*$/.test(num)){
		 var pointIndex =num.lastIndexOf(".");
	     var intPart = num.substring(0,pointIndex);
	     var pointPart =num.substring(pointIndex+1,num.length);
	     intPart = intPart +"";
		     var re =/(-?\d+)(\d{3})/;
		     while(re.test(intPart)){
			          intPart =intPart.replace(re,"$1,$2");
		     }
			      num = intPart+"."+pointPart;
	}else{
		 num = num +"";
		 var re =/(-?\d+)(\d{3})/;
		 while(re.test(num)){
		          num =num.replace(re,"$1,$2");
		 }
	}
	return num;
}

/**
 * 去除千分位
 * 
 * @param{Object}num
 */
function delcommafy(num){
   if((num+"").replace(/(^\s*)|(\s*$)/g,   "")==""){
      return"";
   }
   num=num.replace(/,/gi,'');
   return num;
}

// 强制保留2位小数，如：2，会在2后面补上00.即2.00
function toDecimal2(x) { 
	var f = parseFloat(x); 
	if (isNaN(f)) { 
		return false; 
	} 
	var f = Math.round(x*100)/100; 
	var s = f.toString(); 
	var rs = s.indexOf('.'); 
	if (rs < 0) { 
		rs = s.length; 
		s += '.'; 
	} 
	while (s.length <= rs + 2) { 
		s += '0'; 
	} 
	return s; 
} 

// 强制保留6位小数，如：6，会在6后面补上000000.即2.000000
function toDecimal6(x) { 
	var f = parseFloat(x); 
	if (isNaN(f)) { 
		return false; 
	} 
	var f = Math.round(x*1000000)/1000000; 
	var s = f.toString(); 
	var rs = s.indexOf('.'); 
	if (rs < 0) { 
		rs = s.length; 
		s += '.'; 
	} 
	while (s.length <= rs + 6) { 
		s += '0'; 
	} 
	return s; 
} 

/**
 * 用于调试的方法，显示js对象的所有属性
 * 
 * @param{Object}obj
 */
function allProps ( obj ) { 
	// 用来保存所有的属性名称和值
	var props = "" ; 
	// 开始遍历
	for ( var p in obj ){ // 方法
	if ( typeof ( obj [ p ]) == " function " ){ obj [ p ]() ; 
	} else { // p 为属性名称，obj[p]为对应属性的值
	props += p + " = " + obj [ p ] + " /t " ; 
	} } // 最后显示所有的属性
	alert(props);
}

/**
 * 将form表单元素的值序列化成对象
 * 
 * @requires jQuery 
 * @returns object
 */
$.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};
/**
 * 获得字符串的字符长度
 */
function getStrLength (str) {
	 var reLen = 0;
     for (var i = 0; i < str.length; i++) {        
         if (str.charCodeAt(i) < 27 || str.charCodeAt(i) > 126) {
             // 全角
             reLen += 2;
         } else {
             reLen++;
         }
     }
     return reLen;
}

function gotoUrl(url){
    if(document.all){
        var gotoLink = document.createElement('a');
        gotoLink.target = '_blank';
        gotoLink.href = url;
        document.body.appendChild(gotoLink);
        gotoLink .click();
    }
    else window.location.href = url;
}



