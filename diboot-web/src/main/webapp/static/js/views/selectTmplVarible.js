var GET_VARIBLES_API_PREFIX = contextPath + "/msg/messageTmpl/getTmplVaribles/"
var vm = new Vue({
    el: '#app',
    data: {
        tmplCode: $("#code").val(),
        varibles: [],
        content: $("#content").val()
    },
    methods: {
        getTmplVaribles: function(){
            var _this = this
            if (!this.tmplCode){
                this.varibles = []
                return false;
            }
            return $.get(GET_VARIBLES_API_PREFIX + this.tmplCode, function(res){
                if (res.code == 0){
                    _this.varibles = res.data
                } else {
                    toastr.warning(res.msg)
                }
            })
        },
        addVarible: function(varible){
            this.content += varible
            $("#content").focus()
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            this.getTmplVaribles()
            $("#app").show()
        })
    }
})