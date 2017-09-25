//app.js
App({
  menuList: [{ 'menuName': '段子', 'menuUrl': '/pages/info/info_index', 'open_type': 'redirect' }, { 'menuName': '图片', 'menuUrl': '/pages/image/image_list', 'open_type': 'redirect' }, { 'menuName': '更多', 'menuUrl': '/pages/menu/menu', 'open_type': 'navigate' }],

  onLaunch: function() {

    var that = this;
    wx.request({
      url: 'https://devb.100msh.com/softmarket/api/menu/getMenuList',
      dataType: 'json',
      success: function (res) {
        //that.menuList = res.data.data;
      }
    });
  }

})