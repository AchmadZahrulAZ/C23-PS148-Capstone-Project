const express = require("express");
const app = express();
const port = 3000;

app.use(express.json());

// Daftar produk sementara
let products = [
  { id: 1, name: "Produk 1", price: 10 },
  { id: 2, name: "Produk 2", price: 20 },
  { id: 3, name: "Produk 3", price: 30 },
];

// Daftar produk dalam keranjang
let cartItems = [];

// Pencarian produk berdasarkan nama
app.get("/api/products/search", (req, res) => {
  const { name } = req.query;

  if (!name) {
    res.status(400).json({ error: "Parameter name harus diberikan" });
    return;
  }

  const filteredProducts = products.filter((product) =>
    product.name.toLowerCase().includes(name.toLowerCase())
  );
  res.json(filteredProducts);
});

// Mendapatkan daftar produk
app.get("/api/products", (req, res) => {
  res.json(products);
});

// Mendapatkan detail produk berdasarkan ID
app.get("/api/products/:id", (req, res) => {
  const id = parseInt(req.params.id);
  const product = products.find((p) => p.id === id);

  if (product) {
    res.json(product);
  } else {
    res.status(404).json({ error: "Produk tidak ditemukan" });
  }
});

// Menambahkan produk ke keranjang
app.post("/api/cart", (req, res) => {
  const { productId, quantity } = req.body;
  const product = products.find((p) => p.id === productId);

  if (!product) {
    res.status(404).json({ error: "Produk tidak ditemukan" });
    return;
  }

  const cartItem = {
    productId: product.id,
    name: product.name,
    price: product.price,
    quantity: quantity || 1,
  };

  cartItems.push(cartItem);
  res.status(201).json(cartItem);
});

// Mendapatkan daftar produk dalam keranjang
app.get("/api/cart", (req, res) => {
  res.json(cartItems);
});

// Menghapus produk dari keranjang
app.delete("/api/cart/:productId", (req, res) => {
  const productId = parseInt(req.params.productId);
  const index = cartItems.findIndex((item) => item.productId === productId);

  if (index !== -1) {
    cartItems.splice(index, 1);
    res.status(204).json({ message: "Produk telah terhapus dari keranjang" });
  } else {
    res.status(404).json({ error: "Produk tidak ditemukan dalam keranjang" });
  }
});

// Menghitung total harga pembelian
app.get("/api/cart/total", (req, res) => {
  const total = cartItems.reduce(
    (acc, item) => acc + item.price * item.quantity,
    0
  );
  res.json({ total });
});

// Menyelesaikan pembelian produk di keranjang
app.post("/api/cart/checkout", (req, res) => {
  if (cartItems.length === 0) {
    const response = {
      success: false,
      message: "Tidak ada produk di keranjang",
    };
    return res.status(400).json(response);
  }

  try {
    // Proses pembayaran dan lainnya...
    // Setelah pembelian berhasil, Anda dapat mengosongkan keranjang:
    cartItems = [];
    const response = {
      success: true,
      message: "Pembelian Berhasil",
    };
    res.status(200).json(response);
  } catch (error) {
    console.error("Terjadi kesalahan saat checkout:", error);
    const response = {
      success: false,
      message: "Terjadi kesalahan saat checkout",
    };
    res.status(500).json(response);
  }
});

app.listen(port, () => {
  console.log(`Server berjalan pada http://localhost:${port}`);
});
