const express = require('express');
const app = express();

const admin = require("firebase-admin");
const credentials = require("./key.json");
const jwt = require("jsonwebtoken");

admin.initializeApp({credential: admin.credential.cert(credentials)});

const firebaseConfig = {
  apiKey: "AIzaSyAjpLR85ugwRU5l_JgqVp6KVTEd97HZu_M",
  authDomain: "capstone-c23-ps148.firebaseapp.com",
  databaseURL: "https://capstone-c23-ps148-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "capstone-c23-ps148",
  storageBucket: "capstone-c23-ps148.appspot.com",
  messagingSenderId: "328962493897",
  appId: "1:328962493897:web:d220685510edb1cbd0ac18",
  measurementId: "G-JZ5THDRV3H"
};

app.use(express.json());

app.use(express.urlencoded({extended: true}));

const db = admin.firestore();

//For Generate Custom ID
const ALPHABET = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
const NUMBER_LENGTH = 5;

// Generate a random number with the specified length
function generateNumber(length) {
  let number = '';
  for (let i = 0; i < length; i++) {
    number += Math.floor(Math.random() * 10).toString();
  }
  return number;
}

// Generate a custom ID with the specified prefix and number length
function generateCustomId(prefix, numberLength) {
  const randomNumber = generateNumber(numberLength);
  return prefix + randomNumber;
}


// --MENTEE TABLE--
// Register Mentee
app.post('/mentee/register', async (req, res) => {
  try {
    console.log(req.body);
    const prefix = 'B'; // Editable
    const id = generateCustomId(prefix, NUMBER_LENGTH);
    const menteeJson = {
      ID_MENTEE: id,
      username: req.body.username,
      email: req.body.email,
      phone: req.body.phone,
      password: req.body.password,
      location: req.body.location
    };
    const menteeDb = db.collection('mentee');
    const response = await menteeDb.doc(id).set(menteeJson);
    res.status(200).send('Register berhasil');
  } catch (error) {
    console.error(error);
    res.status(500).send('Terjadi kesalahan dalam registrasi');
  }
});

// Login
app.post("/login", (req, res) => {
  const menteeJson = {
    email: req.body.email,
    password: req.body.password
  }

  // Cari pengguna dengan email yang sesuai
  db.collection("mentee")
    .where("email", "==", email)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        return res.status(404).json({ error: "Email atau kata sandi salah" });
      }

      const mentee = snapshot.docs[0].data();

      // Periksa kecocokan kata sandi menggunakan bcrypt
      bcrypt.compare(password, mentee.password, (err, result) => {
        if (err || !result) {
          return res.status(401).json({ error: "Email atau kata sandi salah" });
        }

        // Buat token otentikasi menggunakan JSON Web Token (JWT)
        const token = jwt.sign({ menteeId: mentee.id } /*process.env.SECRET_KEY*/);
        // Simpan data login pengguna dalam sesi
        req.session.user = {
          loggedIn: true,
          menteeId: mentee.id,
          menteeDocId: snapshot.docs[0].id,
        };

        res.json({ message: "Login berhasil", token });
      });
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat login:", error);
      res.status(500).json({ error: "Terjadi kesalahan saat login" });
    });
});

//Read All Mentee
app.get('/mentee/read/all', async (req, res) => {
  try {
    const menteeRef = db.collection("mentee");
    const response = await menteeRef.get();
    let responseArr = [];
    response.forEach(doc => {
      responseArr.push(doc.data());
    });
    res.send(responseArr);
  }
  catch(error) {
    res.send(error);
  }
});

//Read Mentee by ID's
app.get('/mentee/read/:id', async (req, res) => {
  try {
    const menteeRef = db.collection("mentee").doc(req.params.id);
    const response = await menteeRef.get();
    res.send(response.data());
  }
  catch(error) {
    res.send(error);
  }
});

// Update Mentee by ID
app.post('/mentee/update/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const menteeRef = db.collection("mentee").doc(id);

    await menteeRef.update({
      username: req.body.username,
      email: req.body.email,
      phone: req.body.phone,
      password: req.body.password,
      location: req.body.location
    });

    res.send("Mentee updated successfully");
  } catch (error) {
    res.send(error);
  }
});


//Delete Mentee by ID's
app.delete('/mentee/delete/:id', async (req, res) => {
  try {
    const response = await db.collection("mentee").doc(req.params.id).delete();
    res.send(response);
  }
  catch(error) {
    res.send(error);
  }
});


// --MENTOR TABLES--
// Register Mentor
app.post('/mentor/register', async (req, res) => {
  try {
    console.log(req.body);
    const prefix = 'A'; // Editable
    const id = generateCustomId(prefix, NUMBER_LENGTH);
    const mentorJson = {
      ID_MENTOR: id,
      name: req.body.name,
      email: req.body.email,
      phone: req.body.phone,
      location: req.body.location
    };
    const mentorDb = db.collection('mentor');
    const response = await mentorDb.doc(id).set(mentorJson);
    res.status(200).send('Register Mentor berhasil');
  } catch (error) {
    console.error(error);
    res.status(500).send('Terjadi kesalahan dalam register mentor');
  }
});


//Read All Mentor
app.get('/mentor/read/all', async (req, res) => {
  try {
    const mentorRef = db.collection("mentor");
    const response = await mentorRef.get();
    let responseArr = [];
    response.forEach(doc => {
      responseArr.push(doc.data());
    });
    res.send(responseArr);
  }
  catch(error) {
    res.send(error);
  }
});

//Read Mentor by ID's
app.get('/mentor/read/:id', async (req, res) => {
  try {
    const mentorRef = db.collection("mentor").doc(req.params.id);
    const response = await mentorRef.get();
    res.send(response.data());
  }
  catch(error) {
    res.send(error);
  }
});

// Update Mentor by ID's
app.post('/mentor/update/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const mentorDb = db.collection('mentor').doc(id);

    await mentorDb.update({
      name: req.body.name,
      email: req.body.email,
      phone: req.body.phone,
      location: req.body.location,
    });

    res.send('Mentor updated successfully');
  } catch (error) {
    res.send(error);
  }
});

// Delete Mentor by ID's
app.delete('/mentor/delete/:id', async (req, res) => {
  try {
    const response = await db.collection('mentor').doc(req.params.id).delete();
    res.send(response);
  } catch (error) {
    res.send(error);
  }
});


//Favorit
// Register Favorit
app.post('/favorit/register', async (req, res) => {
  try {
    console.log(req.body);
    const prefix = 'F'; // Editable
    const id = generateCustomId(prefix, NUMBER_LENGTH);
    const favoritJson = {
      ID_FAVORIT: id,
      servicename: req.body.servicename,
      duration: req.body.duration,
      date: req.body.date,
      time: req.body.time,
      method: req.body.method,
    };
    const favoritDb = db.collection('favorit');
    const response = await favoritDb.doc(id).set(favoritJson);
    res.status(200).send('Register Favorit berhasil');
  } catch (error) {
    console.error(error);
    res.status(500).send('Terjadi kesalahan dalam register favorit');
  }
});

//Read All Favorit
app.get('/favorit/read/all', async (req, res) => {
  try {
    const favoritDb = db.collection('favorit');
    const response = await favoritDb.get();
    let responseArr = [];
    response.forEach((doc) => {
      responseArr.push(doc.data());
    });
    res.send(responseArr);
  } catch (error) {
    res.send(error);
  }
});

//Read Favorit by ID's
app.get('/favorit/read/:id', async (req, res) => {
  try {
    const favoritDb = db.collection('favorit').doc(req.params.id);
    const response = await favoritDb.get();
    res.send(response.data());
  } catch (error) {
    res.send(error);
  }
});

// Update Favorit by ID's
app.post('/favorit/update/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const favoritDb = db.collection('favorit').doc(id);

    await favoritDb.update({
      servicename: req.body.servicename,
      duration: req.body.duration,
      date: req.body.date,
      time: req.body.time,
      method: req.body.method,
    });

    res.send('Favorit updated successfully');
  } catch (error) {
    res.send(error);
  }
});

//Delete Favorit by ID's
app.delete('/favorit/delete/:id', async (req, res) => {
  try {
    const response = await db.collection('favorit').doc(req.params.id).delete();
    res.send(response);
  } catch (error) {
    res.send(error);
  }
});


// --MENTORING--
// Create Mentoring
app.post('/mentoring/create', async (req, res) => {
  try {
    console.log(req.body);
    const prefix = 'M'; // Editable
    const id = generateCustomId(prefix, NUMBER_LENGTH);
    const mentoringJson = {
      ID_MENTORING: id,
      title: req.body.title,
      desc: req.body.desc,
      category: req.body.category,
      price: req.body.price
    };
    const mentoringDb = db.collection('mentoring');
    const response = await mentoringDb.doc(id).set(mentoringJson);
    res.status(200).send('Tambah Mentoring berhasil');
  } catch (error) {
    console.error(error);
    res.status(500).send('Terjadi kesalahan dalam penambahan mentoring');
  }
});

// Read All Mentoring (Display All MEntoring)
app.get('/mentoring/read/all', async (req, res) => {
  try {
    const mentoringRef = db.collection("mentoring");
    const response = await mentoringRef.get();
    let responseArr = [];
    response.forEach(doc => {
      responseArr.push(doc.data());
    });
    res.send(responseArr);
  }
  catch(error) {
    res.send(error);
  }
});


// Transaksi
// Register Transaksi
app.post('/transaksi/register', async (req, res) => {
  try {
    console.log(req.body);
    const prefix = 'T'; // Editable
    const id = generateCustomId(prefix, NUMBER_LENGTH);
    const transaksiJson = {
      ID_TRANSAKSI: id,
      transactiondate: req.body.transactiondate,
    };
    const transaksiDb = db.collection('transaksi');
    const response = await transaksiDb.doc(id).set(transaksiJson);
    res.status(200).send('Register Transaksi berhasil');
  } catch (error) {
    console.error(error);
    res.status(500).send('Terjadi kesalahan dalam register transaksi');
  }
});

// Read All Transaksi
app.get('/transaksi/read/all', async (req, res) => {
  try {
    const transaksiDb = db.collection('transaksi');
    const response = await transaksiDb.get();
    let responseArr = [];
    response.forEach((doc) => {
      responseArr.push(doc.data());
    });
    res.send(responseArr);
  } catch (error) {
    res.send(error);
  }
});

// Read Transaksi by ID's
app.get('/transaksi/read/:id', async (req, res) => {
  try {
    const transaksiDb = db.collection('transaksi').doc(req.params.id);
    const response = await transaksiDb.get();
    res.send(response.data());
  } catch (error) {
    res.send(error);
  }
});

// Update Transaksi by ID's
app.post('/transaksi/update/:id', async (req, res) => {
  try {
    const id = req.params.id;
    const transaksiDb = db.collection('transaksi').doc(id);

    await transaksiDb.update({
      transactiondate: req.body.transactiondate,
    });

    res.send('Transaksi updated successfully');
  } catch (error) {
    res.send(error);
  }
});

// Delete Transaksi by ID's
app.delete('/transaksi/delete/:id', async (req, res) => {
  try {
    const response = await db.collection('transaksi').doc(req.params.id).delete();
    res.send(response);
  } catch (error) {
    res.send(error);
  }
});


// --PAYMENT TABLE--
// Get Payment Method
app.get('/payment/:id', async (req, res) => {
  try {
    const paymentRef = db.collection('payment').doc(req.params.id);
    const response = await paymentRef.get();
    res.send(response.data());
  } 
  catch (error) {
    res.send(error);
  }
});


//SEARCH
// Pencarian produk berdasarkan nama
app.get("/search", (req, res) => {
  const { title } = req.query;

  if (!title) {
    res.status(400).json({ error: "Parameter name harus diberikan" });
    return;
  }

  db.collection("mentoring")
    .get()
    .then((snapshot) => {
      const mentoring = [];
      snapshot.forEach((doc) => {
        const display = doc.data();
        if (display.title.toLowerCase().includes(title.toLowerCase())) {
          mentoring.push(display);
        }
      });
      res.json(mentoring);
    })
    .catch((error) => {
      console.error("Terjadi kesalahan saat mencari produk:", error);
      res.status(500).json({ error: "Terjadi kesalahan saat mencari produk" });
    });
});





//PORT
const PORT = process.env.PORT || 8081;
app.listen(PORT, () => {
    console.log(`Server is running on PORT ${PORT}.`);
})