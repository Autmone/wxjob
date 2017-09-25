
var yStart = 0;
var yEnd = 0;
var isBottom = false;

function getImageList(that) {
  var time = new Date().getTime();
  wx.request({
    url: 'https://m.neihanshequ.com/pic/?is_json=1&app_name=neihanshequ_web&max_time=' + time + '&min_time=' + time,
    dataType: 'json',
    header: {
      'Cookie': 'uuid="w:c7739f3a0a05466f8b100ef7dcc7cf0c"; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947701; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947706; tt_webid=58507977591; csrftoken=624bf20b89953cca963c8b7411c019c5; skip_guidence=1'
    },
    success: function (res) {
      var list = res.data.data.data;
      if (list.length > 0) {
        wx.showToast({
          title: '更新了' + 5 + "条",
          icon: 'loading',
          duration: 1000
        })
        var datas = [];
        for (var index = 0; index < 5; index++) {
            datas.push(list[index].group.large_image.url_list[0]);
        }

        that.setData({
          lists: datas
        })

      } else {
        wx.showToast({
          title: '没有更多了',
          icon: 'loading',
          duration: 1000
        })
      }
      wx.stopPullDownRefresh();
    }
  })
}

Page({
  data: {
    lists: [],
    menuList: []
  },

  onLoad: function () {

    this.setData({
      menuList: getApp().menuList
    })

    var that = this;
    var time = new Date().getTime();
    wx.request({
      url: 'https://m.neihanshequ.com/pic/?is_json=1&app_name=neihanshequ_web&max_time=' + time + '&min_time=' + time,
      dataType: 'json',
      header: {
        'Cookie': 'uuid="w:c7739f3a0a05466f8b100ef7dcc7cf0c"; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947701; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947706; tt_webid=58507977591; csrftoken=624bf20b89953cca963c8b7411c019c5; skip_guidence=1'
      },
      success: function (res) {
        var list = res.data.data.data;
        var datas = [];
        for (var i = 0; i < 5; i++) {
          datas.push(list[index].group.large_image.url_list[0]);
        }
        that.setData({
          lists: datas
        })
      }
    });
  },

  onPullDownRefresh: function () {
    //下拉事件处理
    var that = this;
    getImageList(that);
  },

  onShareAppMessage: function (res) {
    console.log(res)
    return {
      title: '灵感空间-爆笑图片',
      path: '/pages/image/image_list',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
  onReachBottom: function () {
    isBottom = true;
  },
  touchstart: function (even) {
    yStart = even.changedTouches[0].clientY;
  },

  touchend: function (even) {

    yEnd = even.changedTouches[0].clientY;

    if (yStart - yEnd > 100 && isBottom) {
      isBottom = false;
      var that = this;
      getImageList(that);
    }

  }

})