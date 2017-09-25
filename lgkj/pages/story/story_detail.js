var storyId;
var page = 1;
var pageSize = 10;
var storyTitle = '';

var util = require('../../utils/util.js');

function pushCommentF(token, that, commentContent) {

  wx.request({
    url: 'https://devb.100msh.com/softmarket/api/comment/addComment?token=' + token,
    method: 'POST',
    data: {
      commentContent: commentContent,
      type: 1,
      typeId: storyId
    },
    success: function (res) {

      if (res.data.code == 0) {
        wx.showModal({
          title: '提示',
          content: '评论成功',
          showCancel: false,
          success: function (res) {
            return;
          }
        })

        //刷新列表
        wx.request({
          url: 'https://devb.100msh.com/softmarket/api/comment/getCommentList?type=1&typeId=' + storyId + '&page=' + page + '&pageSize=' + pageSize,
          dataType: 'json',
          success: function (res) {
            console.log(res.data.data.list)
            that.setData({
              commentList: res.data.data.list
            })
          }
        })

        event.detail.value = "";


      } else if (res.data.code == 400002) {
        util.checkLogin(pushCommentF, that);
      } else {
        wx.showModal({
          title: '提示',
          content: res.data.msg,
          showCancel: false,
          success: function (res) {
            return;
          }
        })
      }
    },
    fail: function (res) {
      wx.showModal({
        title: '提示',
        content: '评论失败',
        showCancel: false,
        success: function (res) {
          return;
        }
      })
    }
  })
}

Page({
  data: {
    item: {},
    commentList:[]
  },

  onLoad: function (options) {
    var that = this;
    storyId = options.story_id;

    wx.request({
      url: 'https://devb.100msh.com/softmarket/api/story/getStoryDetail?storyId=' + storyId,
      dataType: 'json',
      success: function (res) {
        that.setData({
          item: res.data.data
        });
        storyTitle = res.data.data.storyTitle;
        wx.setNavigationBarTitle({
          title: storyTitle
        })
      }
    })

    wx.request({
      url: 'https://devb.100msh.com/softmarket/api/comment/getCommentList?type=1&typeId=' + storyId + '&page=' + page + '&pageSize=' + pageSize,
      dataType: 'json',
      success: function (res) {
        
        that.setData({
          commentList: res.data.data.list
        })
      }
    })
  },

  pushComment: function (event) {

    var commentContent = event.detail.value;
    var that = this;
    wx.getStorage({
      key: 'token',
      success: function (res) {
        //session 未过期，并且在本生命周期一直有效
        var token = res.data;
        pushCommentF(token, that, commentContent);
      },
      fail: function () {
        util.checkLogin(pushCommentF, that, commentContent);
      }
    })
  },
  onShareAppMessage: function (res) {
    console.log(res)
    return {
      title: '灵感空间-' + storyTitle,
      path: '/pages/story/story_detail?story_id=' + storyId,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
})