<?php 

 //Mendapatkan Nilai ID
 $id = $_POST['kategori_id'];
 //$id = $_GET['kategori_id'];
 
 //Import File Koneksi Database
 require_once('koneksi.php');
 //unlink($foto);
 //echo file_get_contents($foto);
 //Membuat SQL Query
  $sql = "DELETE FROM tbl_kategori WHERE id_kategori='$id';";
 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Kategori';
 }else{
 echo 'Gagal Menghapus Kategori';
 }
 
 mysqli_close($con);
 ?>
