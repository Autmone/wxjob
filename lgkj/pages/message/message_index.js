
var util = require('../../utils/util.js');
var page = 1;
var pageSize = 10;
var msgListHis = [];
var xStart = 0;
var xEnd = 0;
var yStart = 0;
var yEnd = 0;

function getMessageList(token, that) {
  if (page > 10) {
    wx.showToast({
      title: '没有更多消息了',
      icon: 'loading',
      duration: 1000
    })
  }
  wx.request({
    url: 'https://devb.100msh.com/softmarket/api/message/getMsgListByPage?token=' + token + '&page=' + page + '&pageSize=' + pageSize,
    dataType: 'json',
    method: 'GET',
    success: function (res) {

      if (res.data.code == 0) {
        if (res.data.data.length < 1) {
          page = page - 1;
          wx.showToast({
            title: '没有更多消息了',
            icon: 'loading',
            duration: 1000
          })
        }

        for (var index = 0; index < res.data.data.length; index++) {
          msgListHis.push(res.data.data[index]);
        }
        that.setData({
          msgList: msgListHis
        })
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
function delMsg(token, that, delMsgPid) {
  wx.request({
    url: 'https://devb.100msh.com/softmarket/api/message/delMessage?token=' + token,
    dataType: 'json',
    method: 'POST',
    data: {
      msgPid: delMsgPid
    },
    success: function (res) {
      msgListHis = [];
      getMessageList(token, that);
    }
  })
};

function restoreMove(that) {
  var animation = wx.createAnimation({
    duration: 1000,
    timingFunction: 'step-start',
  })
  animation.translateX(0).step()
  that.setData({
    animationData: animation.export(),
    delZIndex: '-1'
  })
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    msgList : [],
    animationData:{},
    delZIndex : '-1'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    msgListHis = [];
    var that = this;

    wx.getStorage({
      key: 'token',
      success: function (res) {
        //session 未过期，并且在本生命周期一直有效
        var token = res.data;

        getMessageList(token, that);
      },
      fail: function () {
        util.checkLogin(getMessageList, that);

      }
    })
  },

  getDetail(e) {

    wx.navigateTo({
      url: '../message/message_detail?msgPid=' + e.currentTarget.id
    })

  },

  onPullDownRefresh: function () {
    //下拉事件处理
    var that = this;
    msgListHis = [];
    wx.getStorage({
      key: 'token',
      success: function (res) {
        //session 未过期，并且在本生命周期一直有效
        var token = res.data;

        getMessageList(token, that);

      },
      fail: function () {
        util.checkLogin(getMessageList, that);

      }
    })
    setTimeout(wx.stopPullDownRefresh,1000);
  },
  delMessage: function (even) {
    console.log(even)
    var delMsgPid = even.currentTarget.id;
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确认删除当前对话？',
      showCancel: true,
      success: function (res) {
        if (res.confirm) {
          wx.getStorage({
            key: 'token',
            success: function (res) {
              //session 未过期，并且在本生命周期一直有效
              var token = res.data;

              delMsg(token, that, delMsgPid);
            },
            fail: function () {
              util.checkLogin(delMsg, that, delMsgPid);
            }
          })
        }
        restoreMove(that);
      }
    })
  },
  touchstart: function (even) {
    xStart = even.changedTouches[0].clientX;
    yStart = even.changedTouches[0].clientY;
  },
  
  touchend: function (even) {

    xEnd = even.changedTouches[0].clientX;
    yEnd = even.changedTouches[0].clientY;

    if (xStart - xEnd > 60) {

      var animation = wx.createAnimation({
        duration: 1000,
        timingFunction: 'step-start',
      })
      animation.translateX(-55).step()

      this.setData({
        animationData: animation.export(),
        delZIndex: '1'
      }) 
    } else if (xEnd- xStart > 60) {
      restoreMove(this);
    }

    if (yStart - yEnd > 100) {
      var that = this;
      page = page + 1;
      wx.getStorage({
        key: 'token',
        success: function (res) {
          //session 未过期，并且在本生命周期一直有效
          var token = res.data;

          getMessageList(token, that);
        },
        fail: function () {
          util.checkLogin(getMessageList, that);

        }
      })
    }

  }
})