function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatTime: formatTime
}


function checkLogin(successFunction, that, data) {
  wx.login({
    success: function (res) {
      if (res.code) {
        //发起网络请求
        wx.request({
          url: 'https://devb.100msh.com/softmarket/api/user/wxLogin',
          method: 'POST',
          data: {
            code: res.code
          },
          success: function (res) {
            //存cookie
            var token = res.data.data.token;
            wx.setStorage({
              key: "token",
              data: token
            })


            if (res.data.data.wx_info == 0) {
              wx.getUserInfo({
                success: function (res) {
                  var userInfo = res.userInfo
                  var nickName = userInfo.nickName
                  var avatarUrl = userInfo.avatarUrl
                  var gender = userInfo.gender //性别 0：未知、1：男、2：女
                  var province = userInfo.province
                  var city = userInfo.city
                  var country = userInfo.country;

                  wx.request({
                    url: 'https://devb.100msh.com/softmarket/api/user/updateWXUser?token=' + token,
                    method: 'POST',
                    data: {
                      wxNickName: nickName,
                      avatarUrl: avatarUrl,
                      gender: gender,
                      province: province,
                      city: city,
                      country: country
                    },
                    success: function () {

                    }
                  })
                }
              })
            }
            successFunction(token, that, data);
            return token;
          }
        })
      } else {
        wx.showModal({
          title: '提示',
          content: "授权失败",
          showCancel: false,
          success: function (res) {
            return;
          }
        })
        return false;
      }
    }
  });

};

module.exports.checkLogin = checkLogin