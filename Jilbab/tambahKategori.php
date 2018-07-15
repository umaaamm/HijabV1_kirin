
<?php
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$name = $_POST['kategori_nama'];
		
		$id = chr(rand(65,90)).rand(1,10).chr(rand(65,90));

		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbl_kategori (id_kategori,nama_kategori) VALUES ('$id','$name')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Kategori';
		}else{
			echo 'Gagal Menambahkan Kategori';
		}
		
		mysqli_close($con);
	}
?>

