var getSystemConfigURL = contextPath + '/systemConfig/load/';
var saveSystemConfigURl = contextPath + '/systemConfig/save';
var gotoUrl = contextPath + '/systemConfig/view/'

new Vue({
  el: '#form',
  data: {
    config: {
      id: $('#modelId').val() || '',
      relObjType: '',
      relObjId: '',
      category: '',
      subcategory: '',
      extdata: {}
    },
    defaultObj: {'': ''},
    changeList: []
  },
  mounted: function () {
    var _this = this;
    if(_this.config.id){
      _this.getSystemConfig(_this.config.id)
    }else{
      Object.keys(_this.defaultObj).forEach(function (key) {
        _this.changeList.push({n: key, v: _this.defaultObj[key]})
      });
    }
  },
  methods: {
    //获取原始数据
    getSystemConfig: function (id) {
      var _this = this
      $.ajax({
        url: getSystemConfigURL + id,
        type: 'GET',
        success: function (res) {
          console.log(res);
          if(res.code == 0){
            _this.config.relObjType = res.data.relObjType
            _this.config.relObjId = res.data.relObjId
            _this.config.category = res.data.category
            _this.config.subcategory = res.data.subcategory
            if(res.data.extdata){
              _this.defaultObj = JSON.parse(res.data.extdata)
            }
            Object.keys(_this.defaultObj).forEach(function (key) {
              _this.changeList.push({n: key, v: _this.defaultObj[key]})
            });
          } else{
            toastr.error(res.msg);
          }
        },
        error: function () {
          toastr.error('请求失败！');
        }
      })
    },
    //添加一个参数
    addNewParameter: function (index) {
      console.log();
      if (!this.changeList[index].n) {
        toastr.error('请先填写完整在添加！', '添加失败！');
        return false
      }
      this.changeList.push({n: '', v: ''})
    },
    // 删除一个参数
    deleteOneParameter: function (index) {
      var _this = this;
      if (confirm('确认删除' + this.changeList[index].n + '这个参数配置吗？')) {
        // 如果只剩最后一个，只清空不删除
        if (_this.changeList.length == 1) {
          _this.$set(_this.changeList, 0, {n: '', v: ''});
        } else {
          _this.changeList.splice(index, 1);
        }
      }
    },
    // 判断数组是否重复
    isRepeat: function (arr) {
      var hash = {};
      for (var i in arr) {
        if (hash[arr[i]]) {
          return i;
        }
        hash[arr[i]] = true;
      }
      return false;
    },
    // 提交
    submitForm: function () {
      var _this = this;
      var result = [];
      if (this.config.relObjType == '') {
        result.push('关联对象')
      }
      if ((this.config.relObjId).toString() == '') {
        result.push('关联对象ID')
      }
      if (this.config.category == '') {
        result.push('类别')
      }
      if (this.config.subcategory == '') {
        result.push('子类别')
      }
      if (result.length > 0) {
        toastr.error(result.join(',') + '不能为空', '提交失败！');
        return false
      }
      if (_this.changeList.length > 0 && _this.changeList[0].n != '') {

        //遍历判断是否有参数名重复
        var storage = [];
        _this.changeList.forEach(function (ele) {
          storage.push(ele.n)
        });
        if (_this.isRepeat(storage)) {
          toastr.error(storage[_this.isRepeat(storage)] + '这个参数名已存在，请重新输入', '提交失败！');
          return false
        }
        //最后一个如果参数名不存在提交前直接删除
        if (!_this.changeList[_this.changeList.length - 1].n) {
          _this.changeList.pop()
        }
        //将数组转成对象
        var newObj = {};
        _this.changeList.forEach(function (ele) {
          _this.$set(newObj, ele.n, ele.v)
        });
        _this.config.extdata = JSON.stringify(newObj)
      }
      if(!_this.config.id){
        delete _this.config.id
      }
      $.ajax({
        url: saveSystemConfigURl,
        type:'POST',
        data: _this.config,
        success: function (res) {
          if(res.code == 0){
            if(_this.config.id){
              toastr.success('更新系统配置成功')
            }else{
              toastr.success('创建系统配置成功')
            }
            //成功后1秒跳转到view页面
            setTimeout(function () {
              window.location.href = gotoUrl + res.data.id
            },1000)
          } else{
            toastr.error(res.msg);
          }
        },
        error: function () {
          toastr.error('请求失败！');
        }
      })
    }
  }
});