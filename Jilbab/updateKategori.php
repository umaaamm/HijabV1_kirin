<?php 

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$id = $_POST['kategori_id'];
		$name = $_POST['kategori_nama'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE tbl_kategori SET nama_kategori = '$name' WHERE id_kategori = '$id';";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Kategori';
		}else{
			echo 'Gagal Update Data Kategori';
		}
		
		mysqli_close($con);
	}
?>
