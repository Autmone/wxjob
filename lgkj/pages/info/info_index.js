var page=1;
var pageSize=10;
var yStart = 0 ;
var yEnd = 0;
var isBottom = false;

function getInfoList(that) {
  var time = new Date().getTime();
  wx.request({
    //url: 'https://devb.100msh.com/softmarket/api/information/getInfoList?infoType=666&page='+page+'&pageSize='+pageSize, 
    url: 'https://m.neihanshequ.com/joke/?is_json=1&app_name=neihanshequ_web&max_time=' + time + '&min_time=' + time,
    dataType: 'json',
    header: {
      'Cookie': 'uuid="w:c7739f3a0a05466f8b100ef7dcc7cf0c"; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947701; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947706; tt_webid=58507977591; csrftoken=624bf20b89953cca963c8b7411c019c5; skip_guidence=1'
    },
    success: function (res) {
      var length = res.data.data.data.length;
      if (length > 0) {
        var list = res.data.data.data;
        wx.showToast({
          title: '更新了' + length + "条",
          icon: 'loading',
          duration: 1000
        })
        var datas = [];
        for (var index = 0; index < length; index++) {
          datas.push(list[index].group);
        }
        that.setData({
          lists: datas
        })

      } else {
        page = page - 1;
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
  data:{
    lists : [],
    imgUrls: [
              {url: 'http://devwallet.100msh.com/cooperation_network_img/175/4/20170504183022074.jpg', function: ''},
              {url: 'http://devwallet.100msh.com/cooperation_network_img/175/4/20170504183248017.jpg', function: 'onClickTest1'},
              {url: 'http://devwallet.100msh.com/cooperation_network_img/175/10/20170510110643578.jpg', function: 'onClickTest2'},
              {url: 'http://devwallet.100msh.com/cooperation_network_img/175/10/20170510112757466.jpg', function: 'onClickTest3'}
    ],
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    scrollTop: 0,
    menuList: []
  },

  onShareAppMessage: function (res) {
    return {
      title: '灵感空间-段子、图片、笑话',
      path: '/pages/info/info_index',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },



  onClickTest1: function(event) {
    wx.setStorage({
      key:"testId",
      data:event.currentTarget.id
    });

    wx.navigateTo({
      url: '../tests/test1'
    })
  },
  onClickTest2: function(event) {
    wx.setStorage({
      key:"testId",
      data:event.currentTarget.id
    });
    
    wx.navigateTo({
      url: '../tests/test2'
    })
  },
  onClickTest3: function() {
    wx.navigateTo({
      url: '../tests/test3'
    })
  },
  onLoad: function () {

      var that = this;
      var time = new Date().getTime();
      wx.request({
        //url: 'https://devb.100msh.com/softmarket/api/information/getInfoList?infoType=666&page='+page+'&pageSize='+pageSize, 
        url: 'https://m.neihanshequ.com/joke/?is_json=1&app_name=neihanshequ_web&max_time=' + time + '&min_time=' + time, 
        dataType: 'json',
        header: {
            'Cookie': 'uuid="w:c7739f3a0a05466f8b100ef7dcc7cf0c"; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947701; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1493947706; tt_webid=58507977591; csrftoken=624bf20b89953cca963c8b7411c019c5; skip_guidence=1'
        },
        success: function(res) {
          var list = res.data.data.data;
          var datas = [];
          for(var i=0;i<list.length;i++) {
            datas.push(list[i].group);
          }
          that.setData({
            lists: datas
          })
        }
      });

      wx.request({
        url: 'https://devb.100msh.com/softmarket/api/test/getTestList', 
        dataType: 'json',
        success: function (res) {
          that.setData({
            imgUrls: res.data.data,
            menuList: getApp().menuList
          })
        }
      });
  },
  
  showDetail: function(event) {
    wx.setStorage({
      key:"id",
      data:event.currentTarget.id
    });
    wx.navigateTo({
      url: '../info/info_detail'
    })
  },
  onPullDownRefresh:function(){
    //下拉事件处理
        var that = this;
        page=page+1;
        getInfoList(that);
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
      page = page + 1;
      getInfoList(that);
    }

  }

})