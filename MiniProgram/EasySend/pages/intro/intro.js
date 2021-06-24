// pages/intro/intro.js
Page({
  bunCopy: function(res){
    wx.setClipboardData({
      data: 'http://3a40r96515.zicp.vip/EasySend/getCopyList',
      success (res) {
        // wx.getClipboardData({
        //   success (res) {
        //     console.log(res.data) // data
        //   }
        // })
      }
    })
  },
  bunFile: function(res){
    wx.setClipboardData({
      data: 'http://3a40r96515.zicp.vip/EasySend/getFileList',
      success (res) {
        // wx.getClipboardData({
        //   success (res) {
        //     console.log(res.data) // data
        //   }
        // })
      }
    })
  }
})