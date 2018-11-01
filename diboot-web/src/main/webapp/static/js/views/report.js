/**
 * 报表生成
 */
(function($){
    $(function(){
        $('.chart-box').each(function(index, elem){
            var $this = $(this);

            // 获取每个图表的相关属性
            var id = $this.attr('id')
            var type = $this.data('type');
            var url = $this.data('src');

            // 从后端获取图表数据
            $.getJSON(url, function(data){
                if (data.error){
                    toastr.error('获取数据失败', data.error);
                    return ;
                }

                generateChart(id, type, data);
            })
        });
    });

    /**
     * 生成图标
     * @param id
     * @param data
     */
    function generateChart(domId, type, data){
        var dataList = data['dataList'];

        var labelList = [];
        for (var i=0; i<dataList.length; i++){
            if (dataList[i]['name']){
                labelList.push(dataList[i]['name']);
            }
        }

        var option;
        if (type == 'histogram'){
            option = getHistogramOpt(labelList, dataList);
        } else if (type == 'pie'){
            option = getPieChartOpt(labelList, dataList);
        } else if (type == 'brokenline'){
            option = getBraokenLineChartopt(labelList, dataList);
        }


        var chartBox = echarts.init($('#' + domId)[0]);
        //显示图表
        chartBox.setOption(option);
    }

    /**
     * 初始化柱状图
     * @param domId
     * @param dataList
     */
    function getHistogramOpt(labelList, dataList){
        return {
            title: {
                text: '',//图表标题
                left: 'center' //图表标题位置
            },
            tooltip: {},
            legend: {
                data:[],
                left: 'right'
            },
            //横轴
            xAxis: {
                data: labelList
            },
            // 纵轴
            yAxis: {},
            series: [{
                name: '值',
                type: 'bar',
                data: dataList,
                itemStyle: {
                    normal: {
                        color: function(params) {
                            // 定义预置颜色列表
                            var colorList = [
                                '#64BD3D', '#EE9201', '#29AAE3',
                                '#B74AE5', '#0AAF9F', '#E89589'
                            ];
                            return colorList[params.dataIndex]
                        },
                        label: {
                            show: false
                        }
                    }
                }
            }]
        }
    }

    /**
     * 初始化饼状图
     * @param domId
     * @param dataList
     */
    function getPieChartOpt(labelList, dataList){
        return {
            backgroundColor: '#2c343c',

            title: {
                text: '',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },
            color:['red', 'green','yellow','blueviolet'],
            tooltip : {

            },
            series : [
                {
                    name:'访问来源',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:dataList.sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: function(params) {
                                // 定义预置颜色列表
                                var colorList = [
                                    '#B74AE5', '#0AAF9F','#29AAE3',
                                    '#64BD3D', '#EE9201', '#E89589'
                                ];
                                return colorList[params.dataIndex]
                            }
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
    }

    /**
     * 初始化折线图
     * @param domId
     * @param dataList
     */
    function getBraokenLineChartopt(labelList, dataList){
        return {
            title: {
                text: '',
                subtext: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:[]
            },
            toolbox: {
                show: true
            },
            xAxis:  {
                type: 'category',
                boundaryGap: false,
                data: labelList
            },
            yAxis: {

            },
            series: [
                {
                    name:'值',
                    type:'line',
                    data:dataList,
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        }
    }
})(jQuery);