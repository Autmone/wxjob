var texts= [
      '1、你平时喜欢看什么类型的电视剧？','2、如果有人向你求助，你会伸出援手吗？','3、遇到恃强凌弱的情况时，你会怎么处理？','4、你是一个有领导能力的人吗？','5、你愿意接受挑战吗？','6、你是否觉得目前的生活太过平淡？','7、你是否认同付出一定会有回报这句话？','8、你是一个循规蹈矩的人吗？','9、你是否经常感到钱不够花？','10、你是一个开朗乐观的人吗？'
    ];
var items= [
      [{name: '宫斗剧 ', value: '0'},{name: '武侠剧 ', value: '0'},{name: '生活剧 ', value: '0'}],
      [{name: '会 ', value: '0'},{name: '不会 ', value: '0'},{name: '看情况而定 ', value: '0'}],
      [{name: '马上报警 ', value: '0'},{name: '挺身而出 ', value: '0'},{name: '默默观望 ', value: '0'}],
      [{name: '是 ', value: '0'},{name: '不是 ', value: '0'},{name: '暂时不清楚 ', value: '0'}],
      [{name: '愿意 ', value: '0'},{name: '不愿意 ', value: '0'},{name: '没有想过这个问题 ', value: '0'}],
      [{name: '是 ', value: '0'},{name: '不是 ', value: '0'},{name: '偶尔是 ', value: '0'}],
      [{name: '认同 ', value: '0'},{name: '不认同 ', value: '0'},{name: '要看具体情况 ', value: '0'}],
      [{name: '是 ', value: '1'},{name: '不是 ', value: '0'},{name: '偶尔不是 ', value: '0'}],
      [{name: '是 ', value: '2'},{name: '不是 ', value: '4'},{name: '偶尔是 ', value: '0'}],
      [{name: '是 ', value: '3'},{name: '不是 ', value: '1'},{name: '极少数情况下是 ', value: '4'}]
    ];
    var results = [
        "A、严厉类型.你如果成为班主任的话，一定会是最严厉的那一个，没有之一。也许是曾经学生时代的经历，太过刻骨铭心，所以当你也成为一名班主任后，你肯定会严厉要求每一位学生，力求他们充分体会一下你当年的个中艰辛与痛苦，以此来平衡一下你曾经不满的内心。",
        "B、自由放养类型.正因为你也曾吃过上学的苦，所以当你成为一名班主任后，你会更加体谅学生们的不容易。你不会过分要求他们，相反你还会给予他们最大限度的自由，但这并不表示你不关心他们，只是你的方式更显温和，你始终坚信，每一个学生的身上，都会有着不一样的闪光点。",
        "C、搞笑类型.时代不同，班主任的类型自然也更加多元化。你若成为一名班主任后，自然不愿意再走前人的老路，你更希望自己是一名与众不同的班主任，可以学习气氛两手抓。在不谈学习的情况下，你就是一个风趣搞笑的朋友，不会令学生们觉得压力巨大而不敢与你接触。",
        "D、慈父慈母类型.如果可以有幸成为你的学生，那么他们一定会是非常幸福的。你仿若慈父慈母般的关爱，令学生们体会到另一种家庭的温暖，在你面前，他们与你既是平等的，又是不平等的。平等是因为你尊重他们的人格，不平等则是因为你将他们视为孩子，所以说你确实是一个好班主任。"
    ];
    var index=0;

Page({
  data: {
    text:texts[0],
    item:items[0],
    result:""
  },

  onLoad: function() {
      var that = this;
      wx.getStorage({
        key: 'testId',
        success: function(res) {
            wx.request({
                url: 'https://devb.100msh.com/softmarket/api/test/getTestDetail?testId=' + res.data,
                dataType: 'json',
                success: function(res) {
                    texts = res.data.data.test_question.split(";"),
                    items = res.data.data.test_answer.split(";"),
                    results = res.data.data.test_result.split(";"),
                    
                    that.setData({
                        text:texts[0],
                        item:JSON.parse(items[0])
                    })
                }
            })
        }
      });
  },


  radioChange: function(e) {
        if(e.detail.value == 0) {
            index++;
            this.setData({
                text:texts[index],
                item:JSON.parse(items[index])
            })
        } else if(e.detail.value == 1) {
            this.setData({
                result:results[0]
            })
        } else if(e.detail.value == 2) {
            this.setData({
                result:results[1]
            })
        } else if(e.detail.value == 3) {
            this.setData({
                result:results[2]
            })
        } else if(e.detail.value == 4) {
            this.setData({
                result:results[3]
            })
        }
  }
})