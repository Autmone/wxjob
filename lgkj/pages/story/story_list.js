var page = 1;
var pageSize = 10;
var infoList = [];
Page({
  data: {
    lists: infoList,
    menuList: []
  },

  onLoad: function () {
    infoList = [];
    var that = this;
    var time = new Date().getTime();
    wx.request({
      //url: 'https://devb.100msh.com/softmarket/api/information/getInfoList?infoType=666&page='+page+'&pageSize='+pageSize, 
      url: 'https://devb.100msh.com/softmarket/api/story/getStoryList?min_time=' + time + '&page=' + page + '&pageSize=' + pageSize,
      dataType: 'json',
      success: function (res) {
        var list = res.data.data.list;
        for (var i = 0; i < list.length; i++) {
          infoList.push(list[i]);
        }
        that.setData({
          lists: infoList,
          menuList: getApp().menuList
        })
      }
    });

  },

  showDetail: function (event) {
    wx.navigateTo({
      url: '../story/story_detail?story_id=' + event.currentTarget.id
    })
  },




  onPullDownRefresh: function () {
    //下拉事件处理
    var that = this;
    var time = new Date().getTime();
    page = page + 1;
    wx.request({
      url: 'https://devb.100msh.com/softmarket/api/story/getStoryList?page='+page+'&pageSize='+pageSize, 
      dataType: 'json',
      success: function (res) {
        var length = res.data.data.list.length;
        if (length > 0) {
          var list = res.data.data.list;
          wx.showToast({
            title: '更新了' + length + "条",
            icon: 'loading',
            duration: 1000
          })
          var datas = [];
          for (var index = 0; index < length; index++) {
            datas.push(list[index].group);
          }
          var i = infoList.length > 10 ? 10 : infoList.length;
          console.log(datas);
          console.log(i);
          for (var j = 0; j < i; j++) {
            datas.push(infoList[j]);
          }
          infoList = datas;
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
  },
  onShareAppMessage: function (res) {
    console.log(res)
    return {
      title: '灵感空间-小说、小故事',
      path: '/pages/story/story_list',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },

})