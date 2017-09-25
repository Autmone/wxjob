var basePath = "https://devb.100msh.com/softmarket";

Page({
  data:{
    fileSrc: "",
    menuList: getApp().menuList
  },
  uploadVideo: function() {
      var that = this;
      wx.chooseVideo({
        maxDuration: 20,
        success: function(res) {
            console.log(res);
            var height = res.height;
            var width = res.width;
            if(res.size > 1024*1024*8) {
                wx.showModal({
                  title: '提示',
                  content: '上传的视频大小需要小于8M',
                  showCancel: false,
                  success: function(res) {
                    return;
                  }
                })
            };
            if (res.height==undefined) {
              height="240";
            }
            if (res.width == undefined) {
              width = "350";
            }

            wx.showLoading({
              title: '上传中',
            });

            var tempFilePath = res.tempFilePath;
            wx.uploadFile({
              url: basePath + '/api/video/uploadVideo', 
                filePath: tempFilePath,
                name: 'video',
                formData:{
                  'width': width,
                  'height': height
                },
                success: function(res){
                    console.log(res.data);
                    
                    var fileName = JSON.parse(res.data).data.fileName;
                    wx.hideLoading();
                    wx.showModal({
                      title: '提示',
                      content: '上传成功，你的视频码为：' + fileName + "，是否保存视频分享图片到您的相册？",
                      showCancel: true,
                      success: function(res) {
                        if (res.confirm) {

                          wx.request({
                            url: basePath + '/api/video/getImage?fileName=' + fileName+'&width='+width+'&height='+height,
                            dataType: 'json',
                            success: function (res) {

                              //下载图片
                              wx.downloadFile({
                                url: "https://devb.100msh.com/softmarket/image/" + fileName + "_1.png", 
                                success: function (res) {

                                  console.log(res);
                                  var fileName = res.tempFilePath;
                                  wx.getSetting({
                                    success(res) {
                                      if (!res['scope.writePhotosAlbum']) {
                                        wx.authorize({
                                          scope: 'scope.writePhotosAlbum',
                                          success() {
                                            console.log('用户授权成功');
                                            console.log(res.tempFilePath);
                                            wx.saveImageToPhotosAlbum({
                                              filePath: fileName,
                                              success(res) {
                                                console.log('照片存储成功')
                                                wx.showModal({
                                                  title: '提示',
                                                  content: '照片存储成功',
                                                  showCancel: false
                                                });
                                              },
                                              fail(res) {
                                                wx.showModal({
                                                  title: '提示',
                                                  content: '照片存储失败：' + res.errMsg,
                                                  showCancel: false
                                                });
                                                console.log('照片存储失败：');
                                                console.log(res);
                                              }
                                            })
                                          },
                                          fail() {
                                            console.log('用户取消授权')
                                          }
                                        })
                                      }
                                    }
                                  })
                                }
                              })
                            }
                          })

                        } else if (res.cancel) {
                          console.log('用户点击取消')
                        }
                      }
                    });
                },
                fail: function(res) {
                    console.log(res);
                }
            })
            console.log(222);
        }
    })
  },
  getVideo: function(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value.fileName);
    var fileName = basePath + "/video/" + e.detail.value.fileName;
    this.setData({
      fileSrc:fileName
    })
    
    this.videoContext = wx.createVideoContext('yourVideo')
    this.videoContext.play();

  },


  scanCode: function () {

    wx.scanCode({
      success: function(res){
        var fileName = "http://devb.100msh.com/softmarket/video/" + res.result;

        wx.setStorage({
          key: "video_id",
          data: fileName
        });
        wx.navigateTo({
          url: '../video/video_detail'
        })

      },
      fail: function(res) {
        console.log(res);
      },
      complete: function() {
        // complete
        console.log("com");
      }
    })
  },

  auth: function() {
    wx.getSetting({
      success(res) {
          if (!res['scope.writePhotosAlbum']) {
              wx.authorize({
                  scope: 'scope.writePhotosAlbum',
                  success() {
                  }
              })
          }
       }
    })
  }
})