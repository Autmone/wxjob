// pages/location/location.js

var util = require('../../utils/util.js');
var latitudeVar;
var longitudeVar;
var locationList = [];
var clickUserId = 0;

function getUserByLocal(token, that) {

  wx.getLocation({
    type: 'gcj02', //返回可以用于wx.openLocation的经纬度
    success: function (res) {
      latitudeVar = res.latitude
      longitudeVar = res.longitude

      //上传信息
      wx.request({
        url: 'https://devb.100msh.com/softmarket/api/location/getLocationList?latitude=' + res.latitude + '&longitude=' + res.longitude + '&page=' + 1 + '&pageSize=' + 20 + '&accuracy=' + res.accuracy + '&altitude=' + res.altitude + '&verticalAccuracy=' + res.verticalAccuracy + '&horizontalAccuracy=' + res.horizontalAccuracy + "&token=" + token,
        dataType: 'json',
        success: function (res) {

          if (res.data.code == 0) {
            locationList = res.data.data;
            var datas = [];
            var includePoints = [];

            for (var index = 0; index < locationList.length; index++) {

              var data = {};
              data.id = index;
              data.latitude = locationList[index].latitude;
              data.longitude = locationList[index].longitude;
              data.iconPath = locationList[index].iconPath;
              data.width = 60;
              data.height = 60;

              includePoints.push({ 'latitude': data.latitude, 'longitude': data.longitude });
              datas.push(data);
            }

            that.setData({
              latitude: latitudeVar,
              longitude: longitudeVar,
              markers: datas
            })
          } else if (res.data.code == 400002) {
            util.checkLogin(getUserByLocal, that);
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


    },
    fail: function () {
      wx.showModal({
        title: '提示',
        content: '授权失败',
        showCancel: false,
        success: function (res) {
          return;
        }
      })
    }
  });
};

function pushMessage(token, that, data) {

  wx.request({
    url: 'https://devb.100msh.com/softmarket/api/message/pushMessage?token=' + token,
    dataType: 'json',
    method: 'POST',
    data: {
      toUserId: data.toUserId,
      content: data.content
    },
    success: function (res) {

      if (res.data.code == 0) {
        that.setData({
          msg_value: ''
        });

        wx.showModal({
          title: '提示',
          content: '信息发送成功',
          showCancel: false,
          success: function (res) {
            return;
          }
        })

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
    markers: [],
    include_points: [],
    latitude: 22.543099,
    longitude: 114.057868,
    user_name : '',
    user_gender : '',
    user_city : '',
    user_country : '',
    display:'none',
    map_height : '600px',
    button_display: 'inline-block',
    text_display : 'none',
    msg_value: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    var that = this;

    wx.showModal({
      title: '警告',
      content: '使用此项功能时，表明你也同意其他人能看到您的位置。确认请继续，否则请点击取消。',
      showCancel: true,
      success: function (res) {
        if (res.confirm) {
          wx.getStorage({
            key: 'token',
            success: function (res) {
              //session 未过期，并且在本生命周期一直有效
              var token = res.data;

              getUserByLocal(token, that);
            },
            fail: function () {
              util.checkLogin(getUserByLocal, that);

            }
          })
        } else if (res.cancel) {
          wx.navigateBack({});
          return;
        }
      }
    })
  },

  

  markertap(e) {

    clickUserId = locationList[e.markerId].wxUserId;

    var avatarUrl = '';
    if (locationList[e.markerId].avatarUrl == '') {
      if (locationList[e.markerId].gender == 1) {
        avatarUrl = '../../images/boy.png'
      } else {
        avatarUrl = '../../images/girl.png'
      }
    } else {
      avatarUrl = locationList[e.markerId].avatarUrl;
    }

    this.setData({
      map_height: '350px',
      display: 'block',
      button_display: 'inline-block',
      text_display: 'none',
      user_name: locationList[e.markerId].title,
      user_gender: locationList[e.markerId].gender == 1 ? '男' : (locationList[e.markerId].gender == 2 ? '女' : ''),
      user_image: avatarUrl,
      user_country: locationList[e.markerId].province
    })
  },

  showPushMsg() {
    this.setData({
      button_display: 'none',
      text_display: 'inline-block'
    })
  },

  pushMsg(e) {
    console.log(clickUserId);
    var that = this;
    var data = {};
    data.toUserId = clickUserId;
    data.content = e.detail.value;

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

  hiddenPushMsg: function() {

    this.setData({
      map_height: '600px',
      display: 'none'
    })
  },
  onShareAppMessage: function (res) {
    console.log(res)
    return {
      title: '灵感空间-寻找附近的好友',
      path: '/pages/location/location',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
})