/***
 * 统计图表相关的echart封装
 * @author Duanhl@dibo.ltd
 * @version 2018-06-29
 * Copyright © www.dibo.ltd
 */
/***
 * 柱状图
 * @param data
 * @param customOptions
 */
function barChart(data, customOptions) {
    if (data) {
        var _this = this;
        _this.legend = [];
        _this.x = data[0].values;
        _this.series = [];
        data.forEach(function (ele, i) {
            if(i!=0){
                _this.legend.push(ele.name);
                _this.series.push({name: ele.name, type: 'bar', data: ele.values})
            }
        });
        return {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: _this.legend
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['bar', 'line']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    data: _this.x
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: _this.series
        };
    }
}

/***
 * 折线图
 * @param data
 * @param customOptions
 */
function lineChart(data, customOptions) {
    if (data) {
        var _this = this;
        _this.legend = [];
        _this.x = data[0].values;
        _this.series = [];
        data.forEach(function (ele, i) {
            if(i!=0){
                _this.legend.push(ele.name);
                _this.series.push({name: ele.name, type: 'line', data: ele.values})
            }
        });
         return {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: _this.legend
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    data: _this.x
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: _this.series
        };
    }
}

/***
 * 饼图
 * @param data
 * @param customOptions
 */
function pieChart(data, customOptions) {
    if (data) {
        var _this = this;
        _this.legend = [];
        _this.x = data[0].values;
        _this.dataL = [];
        data[0].values.forEach(function (ele,i) {
            _this.dataL.push({name:'',value:''})
            _this.dataL[i]['name'] = ele
        });
        data[1].values.forEach(function (ele,i) {
            _this.dataL[i]['value'] = ele
        });
        var maxNum = Math.max.apply(null, data[1].values)
        _this.name = data[1].name;
        return {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                data: _this.x
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {
                        show: true, type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                width: '100%',
                                funnelAlign: 'left',
                                max: maxNum + 8
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: _this.name,
                    type: 'pie',
                    radius: '60%',
                    center: ['50%', '60%'],
                    data: _this.dataL
                }
            ]
        };
    }
}

/***
 * 漏斗图
 * @param data
 * @param customOptions
 */
function funnelChart(data, customOptions) {
    if (data) {
        var _this = this;
        _this.x = data[0].values;
        _this.dataL = [];
        data[0].values.forEach(function (ele,i) {
            _this.dataL.push({name:'',value:''})
            _this.dataL[i]['name'] = ele
        });
        data[1].values.forEach(function (ele,i) {
            _this.dataL[i]['value'] = ele
        });
        var maxNum = Math.max.apply(null, data[1].values)
        _this.name = data[1].name;
        return {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c}"
            },
            legend: {
                data: _this.x
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {
                        show: true, type: ['funnel', 'pie'],
                        option: {
                            funnel: {
                                width: '100%',
                                funnelAlign: 'left',
                                max: maxNum + 8
                            },
                            pie:{
                                radius: '60%',
                                center: ['50%', '60%'],
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: _this.name,
                    type:'funnel',
                    width:'100%',
                    funnelAlign: 'left',
                    max: maxNum + 8,
                    data: _this.dataL
                }
            ]
        };
    }
}

/***
 * 仪表板
 * @param data
 * @param customOptions
 */
function gaugeChart(data, customOptions) {
    if (data) {
        var suffix = ''
        if(customOptions && customOptions.suffix){
            suffix = customOptions.suffix
        }
        var _this = this;
        _this.dataL = [];
        _this.dataL.push({value:data[0].values[0],name:data[0].name})
        _this.name = '';
        return {
            tooltip: {
                formatter: "{b} : {c}" + suffix
            },
            toolbox: {
                show: true,
                feature: {
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: _this.name,
                    type:'gauge',
                    detail : {formatter:'{value}'+suffix},
                    data: _this.dataL
                }
            ]
        };
    }
}

//绑定dom事件
jQuery.fn.extend({
    /***
     * 初始化图表
     */
    initChart: function(customOptions){
        var _this = this;
        var chartType = _this.data('type');
        var form = _this.closest('form');
        var btn = form.find('button.btn');
        btn.click(refreshData);
        // 页面初始化后触发一次点击，以显示图表
        refreshData();
        // 重新请求数据并刷新图表
        function refreshData() {
            var paramArray = [];
            form.find('.form-control').each(function (i, ele) {
                paramArray.push($(ele).attr('name') +'='+$(ele).val());
            });
            var url = _this.data('src') + '?' + paramArray.join('&');
            $.ajax({
                type:'GET',
                url: url
            }).done(function (res) {
                // 数据表格直接显示加载的html页面
                if(chartType == "table"){
                    $(_this).empty().append(res);
                }// 初始化图表
                else if(res.code == 0){
                    var myChart = echarts.init($(_this)[0]);
                    var option = {}
                    switch(chartType)
                    {
                        case 'bar':
                            option = new barChart(res.data.dataList, customOptions)
                            break;
                        case 'line':
                            option = new lineChart(res.data.dataList, customOptions)
                            break;
                        case 'pie':
                            option = new pieChart(res.data.dataList, customOptions)
                            break;
                        case 'funnel':
                            option = new funnelChart(res.data.dataList, customOptions)
                            break;
                        case 'gauge':
                            if(!customOptions){
                                customOptions = {suffix: "%"};
                            }
                            option = new gaugeChart(res.data.dataList, customOptions)
                            break;
                        default:
                            break;
                    }
                    myChart.setOption(option);
                }
                else {
                    toastr.error(res.msg, "操作失败")
                }
            }).fail(function (error) {
                toastr.error('请求接口失败', "操作失败")
            })
        }
    }
})
