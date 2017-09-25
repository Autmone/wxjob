Page({
  data:{
    item : {}
  },
  onLoad: function () {
      var that = this;
      wx.getStorage({
        key: 'id',
        success: function(res) {
            console.log(res.data);
            wx.request({
            url: 'https://devb.100msh.com/softmarket/api/information/getInforDetail?infoId=' + res.data,
            dataType: 'json',
            success: function(res) {
                console.log(res.data.data)
                that.setData({
                    item:res.data.data
                })
            }
            })
        }
      })
      
  }
})