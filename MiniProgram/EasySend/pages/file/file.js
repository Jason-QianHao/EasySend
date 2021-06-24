// pages/file/file.js
Page({

  chooseFile: function(){
    wx.chooseImage({
      success (res) {
        const tempFilePaths = res.tempFilePaths
        wx.uploadFile({
          url: 'http://3a40r96515.zicp.vip/EasySend/easyFile', 
          name: 'file',
          success (res){
            const data = res.data;
            console.log(res.log);
            //do something
         }
       })
     }
    })
  }
})