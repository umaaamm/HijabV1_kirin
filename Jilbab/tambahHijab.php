<?php
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$id_kategori = $_POST['kategori_id'];
		$nama_barang = $_POST['barang_nama'];
		$gambar = $_POST['gambar'];
		$harga = $_POST['harga'];
		$stok = $_POST['stok'];
		$deskripsi = $_POST['deskripsi'];
		
		$id = rand(10,100).rand(1,10);

		$nGambar = $id.'.JPEG';
		$path = "uploads/$nGambar";

		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbl_barang (id_barang,id_kategori,nama_barang,gambar,harga,stok,deskripsi) VALUES ('$id','$id_kategori','$nama_barang','$nGambar','$harga','$stok','$deskripsi')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			file_put_contents($path,base64_decode($gambar));
			echo 'Berhasil Menambahkan Pegawai';
		}else{
			echo 'Gagal Menambahkan Pegawai';
		}
		
		mysqli_close($con);
	}
?>
