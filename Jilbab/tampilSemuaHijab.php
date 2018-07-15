<?php 

 
	//Importing database
	require_once('koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM tbl_barang";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_barang"=>$row['id_barang'],
			//"id_kategori"=>$row['id_kategori'],
			"nama_barang"=>$row['nama_barang'],
			//"harga"=>$row['harga'],
			"stok"=> $row['stok'],
			"gambar"=>base64_encode(file_get_contents('./uploads/'.$row['gambar']))
		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	//echo base64_encode(file_get_contents('./uploads/'.$row['foto']));
	mysqli_close($con);
?>
