package com.example.ladm_u4_ejercicio4_mapas_andrsh

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var baseRemota = FirebaseFirestore.getInstance()
    var posicion = ArrayList<Data>()
    lateinit var locacion : LocationManager
    private lateinit var mMap: GoogleMap
    lateinit var ubicacionCliente : FusedLocationProviderClient
    lateinit var player : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }

        baseRemota.collection("GeoTec").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException != null){
               Toast.makeText(this,"Error: "+firebaseFirestoreException.message,Toast.LENGTH_LONG)
                   .show()
                return@addSnapshotListener
            }
            var resultado = ""
            posicion.clear()
            for(document in querySnapshot!!){
                var data = Data()
                data.nombre = document.getString("nombre").toString()
                data.posicion1 = document.getGeoPoint("posicion1")!!
                data.posicion2 = document.getGeoPoint("posicion2")!!

                resultado += data.toString()+"\n\n"
                posicion.add(data)
            }

           // textView.setText(resultado)
        }

        ubicacionCliente = LocationServices.getFusedLocationProviderClient(this)
        locacion = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var oyente = Oyente(this)
        locacion.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,01f,oyente)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            setTitle("Se otorgó permiso")
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        val entIns = LatLng(21.477005, -104.866626)
        mMap.addMarker(MarkerOptions().position(entIns).title("Entrada Peatonal Av. Insurgentes"))

        val bastonOficinas = LatLng(21.477582, -104.866557)
        mMap.addMarker(MarkerOptions().position(bastonOficinas).title("Oficinas Edificio Bastón"))

        val bastonSalones = LatLng(21.477646, -104.867022)
        mMap.addMarker(MarkerOptions().position(bastonSalones).title("Salones Edificio Bastón"))

        val labComputacion = LatLng(21.478038, -104.867157)
        mMap.addMarker(MarkerOptions().position(labComputacion).title("Laboratorio de Computación"))

        val centroIdiomas = LatLng(21.478200, -104.867886)
        mMap.addMarker(MarkerOptions().position(centroIdiomas).title("Centor de Idiomas"))

        val liia = LatLng(21.478860, -104.867568)
        mMap.addMarker(MarkerOptions().position(liia).title("LIIA"))

        val licbi = LatLng(21.479595, -104.867010)
        mMap.addMarker(MarkerOptions().position(licbi).title("LICBI"))

        val entVehi = LatLng (21.476520, -104.865185)
        mMap.addMarker(MarkerOptions().position(entVehi).title("Entrada Vehicular Av. Insurgentes"))

        val ud = LatLng(21.477769, -104.865964)
        mMap.addMarker(MarkerOptions().position(ud).title("Edificio UD"))

        val recFin = LatLng(21.478551, -104.865634)
        mMap.addMarker(MarkerOptions().position(recFin).title("Recursos Financieros"))

        val cenInf = LatLng(21.478629, -104.865337)
        mMap.addMarker(MarkerOptions().position(cenInf).title("Centro de Información"))

        val baCenInf = LatLng(21.478488, -104.865241)
        mMap.addMarker(MarkerOptions().position(baCenInf).title("Baños Centro de Información"))

        val copCenInf = LatLng(21.478550, -104.865281)
        mMap.addMarker(MarkerOptions().position(copCenInf).title("Centro de Copiado Centro de Información"))

        val bibCenInf = LatLng(21.478626, -104.865106)
        mMap.addMarker(MarkerOptions().position(bibCenInf).title("Biblioteca Centro de Información"))

        val comedor = LatLng(21.478866, -104.866208)
        mMap.addMarker(MarkerOptions().position(comedor).title("Comedor ITTepic"))

        val labCE = LatLng(21.478968, -104.866720)
        mMap.addMarker(MarkerOptions().position(labCE).title("Laboratorio Ingeniería Electrica y Civil"))

        val js = LatLng(21.479125, -104.866223)
        mMap.addMarker(MarkerOptions().position(js).title("Edificio J"))

        val kiosko = LatLng(21.479351, -104.865798)
        mMap.addMarker(MarkerOptions().position(kiosko).title("Kiosko ITTepic"))

        val eAs = LatLng(21.479083, -104.864972)
        mMap.addMarker(MarkerOptions().position(eAs).title("Edicio A"))

        val gs = LatLng(21.479352, -104.865087)
        mMap.addMarker(MarkerOptions().position(gs).title("Edicio G"))

        val qs = LatLng(21.479557, -104.865032)
        mMap.addMarker(MarkerOptions().position(qs).title("Edicio Q"))

        val labInd = LatLng(21.480029, -104.866398)
        mMap.addMarker(MarkerOptions().position(labInd).title("Laboratorio Industrial"))

        val volPla = LatLng(21.480333, -104.865486)
        mMap.addMarker(MarkerOptions().position(volPla).title("Cancha Voleibol Playa"))

        val domMul = LatLng(21.481064, -104.865298)
        mMap.addMarker(MarkerOptions().position(domMul).title("Domo Multideportivo"))

        val beisbol = LatLng(21.481660, -104.864480)
        mMap.addMarker(MarkerOptions().position(beisbol).title("Cancha de Beisbol"))

        val futbol = LatLng(21.480649, -104.863951)
        mMap.addMarker(MarkerOptions().position(futbol).title("Canchas de Futbol"))

        val entLagSup = LatLng(21.482749, -104.865675)
        mMap.addMarker(MarkerOptions().position(entLagSup).title("Entrada Lago Superior"))

        val ent2Ago = LatLng(21.479546, -104.867666)
        mMap.addMarker(MarkerOptions().position(ent2Ago).title("Entrada 2 de Agosto"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(entIns))

        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.isMyLocationEnabled = true
        ubicacionCliente.lastLocation.addOnSuccessListener {
            if (it!=null){
                val posicionActual = LatLng(it.latitude,it.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionActual,15f))
            }
        }
    }

    fun poneTexto(texto:String) {
        Toast.makeText(this,texto,Toast.LENGTH_LONG)
            .show()
    }

    fun reproduceSonido(){
        val mp = MediaPlayer.create(this,R.raw.navi)
        mp.prepare()
        mp.start()

        /*try {
            player.setDataSource(R.raw.navi.toString())
            player.prepare()
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
    }
}


class Oyente(puntero:MapsActivity) : android.location.LocationListener{
    var p = puntero
    override fun onLocationChanged(location: Location) {

        //p.setTitle("${location.latitude}, ${location.longitude}")

        var geoposicion = GeoPoint(location.latitude,location.longitude)
        var anterior = ""

        for (item in p.posicion){
            if(item.estoyEn(geoposicion)){
              //  p.textView3.setText("Estas en ${item.nombre}")
                p.setTitle("Estas en ${item.nombre}")

                if(p.title.toString() != anterior && anterior != ""){
                    p.reproduceSonido()
                }
                anterior = p.title.toString()
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

}
