/**
 * Created by duanhailin on 2018/06/21.
 */
;(function ($) {
    function fieldSort(dom) {
        $(dom).css('position', 'relative');
        var html = '<span style="position: absolute;right:20%">' +
            '<i class="fa fa-sort"  style="color:#dadada;position:absolute;top:3px;"></i>' +
            '</span>';
        $(dom).append(html);
        $(dom).css({
            'cursor': 'pointer'
        })
        var url = location.href;
        $(dom).click(function () {
            $(this).siblings('th').find('i').removeClass('fa-caret-up fa-caret-down').addClass('fa-sort').css('color', '#dadada');
            var childI = $(this).find('i');
            var sort = '';
            var field = $(this).data('field');
            if (childI.hasClass('fa-caret-up')) {
                childI.removeClass('fa-caret-up').addClass('fa-caret-down');
                sort = 'DESC';
            } else {
                childI.removeClass('fa-sort').addClass('fa-caret-up');
                sort = 'ASC';
            }
            childI.css('color', '#333');
            if (url.indexOf('?') != -1) {
                if (url.indexOf('_orderBy=') != -1) {
                    var arr1 = url.split('_orderBy=')[0];
                    url = arr1 + '_orderBy=' + field + ':' + sort
                } else {
                    url += '&_orderBy=' + field + ':' + sort
                }
            } else {
                url += '?_orderBy=' + field + ':' + sort
            }
            window.location.href = url;
        });
        var newUrl = location.href;
        var newNeed = '';
        var newSort = '';
        var oldField = '';
        if(newUrl.indexOf('_orderBy=')!= -1){
            newNeed = newUrl.split('_orderBy=')[1];
            newSort = newNeed.split(':')[1];
            oldField = newNeed.split(':')[0];
        }
        $(dom).each(function (index,ele) {
            if($(ele).data('field') == oldField) {
                if(newSort == 'ASC'){
                    $(ele).find('i').removeClass('fa-sort').addClass('fa-caret-up');
                } else if(newSort == 'DESC'){
                    $(ele).find('i').removeClass('fa-sort').addClass('fa-caret-down');
                }else{
                    $(ele).find('i').addClass('fa-sort');
                }
                $(ele).find('i').css('color','#333')
            }
        })
    }

    $.fn.fieldSort = function () {
        fieldSort(this);
    }
})(jQuery);