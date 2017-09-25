
var page = 1;
var pageSize = 5;
var msgPid = 0;
var toUserId = 0;
var msgListHis = [];

function getMessageDetail(token, that, msgPid) {

  if (page > 10) {
    wx.showToast({
      title: '没有更多消息了',
      icon: 'loading',
      duration: 1000
    })
  }

  wx.request({
    url: 'https://devb.100msh.com/softmarket/api/message/getMessagerDetail?token=' + token + '&page=' + page + '&pageSize=' + pageSize + '&msgPid=' + msgPid,
    dataType: 'json',
    method: 'GET',
    success: function (res) {

      if (res.data.code == 0) {
        if (res.data.data.list.length < 1) {
          page = page - 1;
          wx.showToast({
            title: '没有更多消息了',
            icon: 'loading',
            duration: 1000
          })
          return ;
        }

        for (var index = 0; index < res.data.data.list.length; index++) {
          msgListHis.unshift(res.data.data.list[index]);
        }

        that.setData({
          msgList: msgListHis
        })
        toUserId = res.data.data.toUserId;
      } else if (res.data.code == 400002) {
        util.checkLogin(getMessageList, that);
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

    }
  })
};

function pushMessage(token, that, data) {

  wx.request({
    url: 'https://devb.100msh.com/softmarket/api/message/pushMessage?token=' + token,
    dataType: 'json',
    method: 'POST',
    data: {
      toUserId: data.toUserId,
      content: data.content,
      msgPid: data.msgPid
    },
    success: function (res) {

      if (res.data.code == 0) {
        that.setData({
          msg_value: ''
        });

        wx.showToast({
          title: '信息发送成功',
          icon: 'success',
          duration: 1000
        })

        msgListHis = [];
        getMessageDetail(token, that, msgPid);

      } else if (res.data.code == 400002) {
        util.checkLogin(pushMessage, that, data);
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

    }
  })
};

Page({

  /**
   * 页面的初始数据
   */
  data: {
    msgList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    msgListHis = [];
    page = 1;

    msgPid = options.msgPid;
    var that = this;

    wx.getStorage({
      key: 'token',
      success: function (res) {
        //session 未过期，并且在本生命周期一直有效
        var token = res.data;

        getMessageDetail(token, that, msgPid);
      },
      fail: function () {
        util.checkLogin(getMessageDetail, that, msgPid);

      }
    })
  },
  pushMsg: function(e) {
      var data = {};
      data.content = e.detail.value;
      data.msgPid= msgPid;
      data.toUserId = toUserId;

      var that = this;

      wx.getStorage({
        key: 'token',
        success: function (res) {
          //session 未过期，并且在本生命周期一直有效
          var token = res.data;

          pushMessage(token, that, data);
        },
        fail: function () {
          util.checkLogin(pushMessage, that, data);

        }
      })
  },

  onPullDownRefresh: function () {
    //下拉事件处理
    var that = this;
    page = page + 1;
    wx.getStorage({
      key: 'token',
      success: function (res) {
        //session 未过期，并且在本生命周期一直有效
        var token = res.data;

        getMessageDetail(token, that, msgPid);
      },
      fail: function () {
        util.checkLogin(getMessageDetail, that, msgPid);

      }
    })

    wx.stopPullDownRefresh();
  }
})