var texts= []
var items= []
var results = [];

Page({
  data: {
    text:'',
    item:[],
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
                        text:texts[1],
                        item:JSON.parse(items[1])
                    })
                }
            })
        }
      });
  },

  radioChange: function(e) {
        if(e.detail.value == "A") {
            this.setData({
                result:results[0]
            })
        } else if(e.detail.value == "B") {
            this.setData({
                result:results[1]
            })
        } else if(e.detail.value == "C") {
            this.setData({
                result:results[2]
            })
        } else if(e.detail.value == "D") {
            this.setData({
                result:results[3]
            })
        } else {
            this.setData({
                text:texts[e.detail.value],
                item:JSON.parse(items[e.detail.value]),
            })
        }
  }
})