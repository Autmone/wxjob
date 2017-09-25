//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    imgUrls: [
              {url: 'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg', function: 'onClickTest1'},
              {url: 'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg', function: 'onClickTest2'}
    ],
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 1000
  },
  onClickTest1: function() {
    var time = new Date().getTime();
      console.log(time);
    wx.navigateTo({
      url: '../tests/test1'
    })
  },
  onClickTest2: function() {
    wx.navigateTo({
      url: '../tests/test2'
    })
  },
  showInfo:function() {
    wx.navigateTo({
      url: '../info/info_index'
    })
  }
})
