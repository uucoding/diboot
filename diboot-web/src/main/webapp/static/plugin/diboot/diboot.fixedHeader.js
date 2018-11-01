/**
 * 表头固定scripts
 * @author duanhl
 */
(function ($) {
    function FixedHeader(dom,cfg){
        // 只有当页面有dom时才初始化
        if(dom.length>0){
            this.Dom = dom;
            this.defaultColor = this.Dom.find('thead').css('color');
            this.defaultBgColor = this.Dom.find('thead').find('tr').css('backgroundColor');
            if(this.defaultBgColor == 'rgba(0, 0, 0, 0)'){
                this.defaultBgColor = '#fff'
            }
            this.config = cfg;
            this.arrWidth = [];
            if(this.config.topDom){
                this.distanceTop = $(this.config.topDom).height();
            } else {
                this.distanceTop = 0 ;
            }
            this.defaultWidth = this.Dom.find('thead').width();
            //距离顶部距离 = 当前表格距离顶部距离 - 页面固定导航距离顶部距离 - 边框及误差(大致范围即可)
            this.offsetTop = this.Dom.offset().top - this.distanceTop - 2;
            //只有当table父级元素宽度大于table时才触发固定表头
            if(this.Dom.closest('div').width() >= this.Dom.width()){
                this.init();
            }
        }
    }
    FixedHeader.prototype = {
        init: function(){
            var _self = this;
            //获取表格表头的原始宽度
            _self.Dom.find('thead').find('th').each(function (i,v) {
                _self.arrWidth.push(v.offsetWidth)
            });
            _self.scrollFun();
        },
        // 鼠标滚动事件
        scrollFun: function () {
            var _self = this;
            $(document).scroll(function () {
                if ($(document).scrollTop() > _self.offsetTop) {
                    _self.Dom.find('thead').css({
                        'position': 'fixed',
                        'top': _self.distanceTop,
                        'background-color': _self.defaultBgColor,
                        'color': _self.defaultColor,
                        'width': _self.defaultWidth
                    });
                    // 表格表头的宽度和表格第一行宽度仍为为原始宽度
                    _self.Dom.find('thead').find('th').each(function (i,v) {
                        $(v).css({
                            'width':_self.arrWidth[i]
                        })
                    });
                    _self.Dom.find('tbody').find('tr:first').find('td').each(function (i,v) {
                        $(v).css({
                            'width':_self.arrWidth[i]
                        })
                    });
                    _self.Dom.css('margin-top', _self.distanceTop)
                } else {
                    _self.Dom.find('thead').css({
                        'position': '',
                        'top': '',
                        'background-color':'',
                        'color':'',
                        'width':''
                    });
                    _self.Dom.css('margin-top','')
                }
            });
        }
    };
    // 初始化
    $.fn.fixHeader = function(cfg){
        if(!cfg){
            cfg = {
                topDom: '.navbar' //导航class，为了去除高度
            }
        }
        new FixedHeader(this,cfg)
    }
})(jQuery);