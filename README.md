<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Capturas</title>

  <style>
    :root{
      --bg:#0b1220;
      --card:#0f1a30;
      --text:#e7ecff;
      --muted:#a9b4d0;
      --ring: rgba(255,255,255,.08);
      --shadow: 0 14px 40px rgba(0,0,0,.35);
      --radius: 18px;
    }

    body{
      margin:0;
      font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif;
      background: radial-gradient(1200px 600px at 10% 0%, rgba(90,120,255,.25), transparent 60%),
                  radial-gradient(900px 500px at 90% 10%, rgba(0,255,200,.15), transparent 55%),
                  var(--bg);
      color: var(--text);
    }

    .wrap{
      max-width: 1100px;
      margin: 0 auto;
      padding: 28px 16px 44px;
    }

    header{
      display:flex;
      gap:12px;
      align-items:flex-end;
      justify-content:space-between;
      flex-wrap:wrap;
      margin-bottom: 16px;
    }

    h1{
      font-size: clamp(20px, 3vw, 28px);
      margin:0;
      letter-spacing:.2px;
    }

    .hint{
      color: var(--muted);
      margin:0;
      font-size: 14px;
    }

    .grid{
      display:grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 14px;
    }

    figure{
      margin:0;
      background: linear-gradient(180deg, rgba(255,255,255,.06), rgba(255,255,255,.02));
      border: 1px solid var(--ring);
      border-radius: var(--radius);
      overflow:hidden;
      box-shadow: var(--shadow);
      transition: transform .18s ease, border-color .18s ease;
    }

    figure:hover{
      transform: translateY(-2px);
      border-color: rgba(255,255,255,.16);
    }

    /* Botón/preview clickeable */
    .shot{
      display:block;
      position:relative;
      cursor: zoom-in;
      text-decoration:none;
      color:inherit;
    }

    img{
      width:100%;
      height:auto;
      display:block;
      aspect-ratio: 9 / 16;         /* tus capturas son verticales */
      object-fit: cover;
      background: rgba(255,255,255,.03);
      transform: scale(1.01);
      transition: transform .25s ease;
    }

    figure:hover img{
      transform: scale(1.035);
    }

    figcaption{
      padding: 10px 12px 12px;
      font-size: 13px;
      color: var(--muted);
      display:flex;
      align-items:center;
      justify-content:space-between;
      gap:10px;
    }

    .tag{
      font-size: 12px;
      padding: 6px 10px;
      border-radius: 999px;
      border: 1px solid var(--ring);
      background: rgba(255,255,255,.04);
      color: var(--text);
      white-space:nowrap;
    }

    /* “Lightbox” sin JS usando :target */
    .lightbox{
      position:fixed;
      inset:0;
      display:none;
      align-items:center;
      justify-content:center;
      padding: 22px;
      background: rgba(0,0,0,.75);
      backdrop-filter: blur(6px);
      z-index: 999;
    }
    .lightbox:target{ display:flex; }

    .lightbox .panel{
      width:min(980px, 96vw);
      max-height: 92vh;
      border-radius: 20px;
      overflow:hidden;
      border: 1px solid rgba(255,255,255,.12);
      background: rgba(15,26,48,.65);
      box-shadow: 0 22px 60px rgba(0,0,0,.55);
      position:relative;
    }

    .lightbox img{
      width:100%;
      height:auto;
      max-height: 92vh;
      object-fit: contain;
      aspect-ratio: auto;
      transform:none !important;
    }

    .close{
      position:absolute;
      top:10px; right:10px;
      padding: 8px 12px;
      border-radius: 999px;
      border: 1px solid rgba(255,255,255,.14);
      background: rgba(0,0,0,.35);
      color: var(--text);
      text-decoration:none;
      font-size: 13px;
    }
  </style>
</head>

<body>
  <div class="wrap">
    <header>
      <div>
        <h1>Galería de capturas</h1>
        <p class="hint">Responsive + zoom suave + vista completa al hacer clic (sin JS). Sí, está “aesthetic”.</p>
      </div>
    </header>

    <section class="grid">
      <figure>
        <a class="shot" href="#img1">
          <img
            src="https://github.com/user-attachments/assets/4af55255-b95b-45d1-b49e-7ca205ab127e"
            alt="Captura 1"
            loading="lazy"
          />
        </a>
        <figcaption>
          <span>Screenshot_20260213_164338</span>
          <span class="tag">PNG</span>
        </figcaption>
      </figure>

      <figure>
        <a class="shot" href="#img2">
          <img
            src="https://github.com/user-attachments/assets/07916042-e63d-448d-aba5-f5a4e11156da"
            alt="Captura 2"
            loading="lazy"
          />
        </a>
        <figcaption>
          <span>Screenshot_20260213_165131</span>
          <span class="tag">PNG</span>
        </figcaption>
      </figure>

      <!-- Si la tercera es igual, igual la dejo. Si no, cambia el src -->
      <figure>
        <a class="shot" href="#img3">
          <img
            src="https://github.com/user-attachments/assets/07916042-e63d-448d-aba5-f5a4e11156da"
            alt="Captura 3"
            loading="lazy"
          />
        </a>
        <figcaption>
          <span>Screenshot_20260213_165131 (rep)</span>
          <span class="tag">PNG</span>
        </figcaption>
      </figure>
    </section>
  </div>

  <!-- Lightboxes -->
  <div id="img1" class="lightbox">
    <div class="panel">
      <a class="close" href="#">Cerrar ✕</a>
      <img src="https://github.com/user-attachments/assets/4af55255-b95b-45d1-b49e-7ca205ab127e" alt="Captura 1 grande">
    </div>
  </div>

  <div id="img2" class="lightbox">
    <div class="panel">
      <a class="close" href="#">Cerrar ✕</a>
      <img src="https://github.com/user-attachments/assets/07916042-e63d-448d-aba5-f5a4e11156da" alt="Captura 2 grande">
    </div>
  </div>

  <div id="img3" class="lightbox">
    <div class="panel">
      <a class="close" href="#">Cerrar ✕</a>
      <img src="https://github.com/user-attachments/assets/07916042-e63d-448d-aba5-f5a4e11156da" alt="Captura 3 grande">
    </div>
  </div>
</body>
</html>
