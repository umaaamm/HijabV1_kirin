<?php 

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$id = $_POST['barang_id'];
		$id_kategori = $_POST['kategori_id'];
		$nama_barang = $_POST['barang_nama'];
		$gambar = $_POST['gambar'];
		$harga = $_POST['harga'];
		$stok = $_POST['stok'];
		$gambar_bmp = $_POST['gambar_bmp'];

		$path = "uploads/$foto";
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE tbl_barang SET id_kategori = '$id_kategori', nama_barang = '$nama_barang',gambar = '$gambar',harga = '$harga',stok = '$stok' WHERE id_barang = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			file_put_contents($path,base64_decode($foto_bmp));
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>
