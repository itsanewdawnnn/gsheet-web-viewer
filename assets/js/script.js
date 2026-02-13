(function() {
  const allowedOrigin = 'https://itsanewdawnnn.github.io';

  if (window.location.origin !== allowedOrigin) {
    document.documentElement.innerHTML = '';
    document.title = '';
    throw new Error('Akses ditolak.');
  }
})();

const sheetID = '1JZ4OV-qpB_QhyXJMeNR9YRAL5GDuKYdAm62ch7ShteM';
const gid = '371636866';
const url = `https://docs.google.com/spreadsheets/d/${sheetID}/export?format=csv&gid=${gid}`;

function showLoader() {
  document.getElementById('loader').classList.remove('hidden');
}

function hideLoader() {
  document.getElementById('loader').classList.add('hidden');
}

function loadData() {
  showLoader();
  const portfolioThead = document.querySelector('#tabel-portfolio thead');
  const portfolioTbody = document.querySelector('#tabel-portfolio tbody');
  const rekapitulasiThead = document.querySelector('#tabel-rekapitulasi thead');
  const rekapitulasiTbody = document.querySelector('#tabel-rekapitulasi tbody');

  portfolioThead.innerHTML = '';
  portfolioTbody.innerHTML = '';
  rekapitulasiThead.innerHTML = '';
  rekapitulasiTbody.innerHTML = '';

  fetch(url)
    .then(response => response.text())
    .then(csvText => {
      Papa.parse(csvText, {
        complete: function(results) {
          const data = results.data;

          const headRow = document.createElement('tr');
          for (let j = 0; j <= 7; j++) {
            const th = document.createElement('th');
            th.textContent = data[0][j] ?? '';
            headRow.appendChild(th);
          }
          portfolioThead.appendChild(headRow);

          for (let i = 1; i < data.length; i++) {
            const row = data[i];
            const isEmpty = row.slice(0, 8).every(cell => !cell || cell.trim() === '');
            if (isEmpty) continue;
            const tr = document.createElement('tr');
            for (let j = 0; j <= 7; j++) {
              const td = document.createElement('td');
              td.textContent = row[j] ?? '';
              tr.appendChild(td);
            }
            portfolioTbody.appendChild(tr);
          }

          const rekapHeader = document.createElement('tr');
          for (let j = 9; j <= 10; j++) {
            const th = document.createElement('th');
            th.textContent = data[0][j] ?? '';
            rekapHeader.appendChild(th);
          }
          rekapitulasiThead.appendChild(rekapHeader);

          for (let i = 1; i <= 8; i++) {
            const tr = document.createElement('tr');
            for (let j = 9; j <= 10; j++) {
              const td = document.createElement('td');
              td.textContent = data[i]?.[j] ?? '';
              tr.appendChild(td);
            }
            rekapitulasiTbody.appendChild(tr);
          }

          hideLoader();
        }
      });
    })
    .catch(err => {
      hideLoader();
      alert("Gagal mengambil data dari Google Sheets.");
      console.error("Detail error:", err);
    });
}

window.onload = loadData;
