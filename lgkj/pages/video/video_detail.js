Page({
  data: {
    fileSrc: ""
  },
  onLoad: function() {

    var that = this;
    wx.getStorage({
      key: 'video_id',
      success: function (res) {
        console.log(res)

        that.setData({
          fileSrc: res.data
        })
      }
    })

  },
  onReady:function() {
    this.videoContext = wx.createVideoContext('yourVideo')
    this.videoContext.pause();
    this.videoContext.seek(0.01);
    this.videoContext.play();
  }
})