<?php 

 //Mendapatkan Nilai ID
 $id = $_POST['Hijab_id'];
 //$id = $_GET['id'];
 $gambar = './uploads/'.$_POST['gambar'];
 
 //Import File Koneksi Database
 require_once('koneksi.php');
 //unlink($foto);
 //echo file_get_contents($foto);
 //Membuat SQL Query
  $sql = "DELETE FROM tbl_barang WHERE id_barang='$id';";
 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 unlink($gambar);
 echo 'Berhasil Menghapus Barang';
 }else{
 echo 'Gagal Menghapus Barang';
 }
 
 mysqli_close($con);
 ?>
