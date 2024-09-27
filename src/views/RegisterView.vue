<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Konto erstellen</h1>
    </div>
    <div class="form-container register-container">
      <form method="post" id="registrationForm">
        <div class="row">
          <div class="col-md-6">
            <div class="form-floating mb-3">
              <select class="form-select" id="floatingSelectAnrede" name="anrede" required>
                <option value="" disabled selected hidden>Anrede</option>
                <option value="Herr">Herr</option>
                <option value="Frau">Frau</option>
              </select>
              <label for="floatingSelectAnrede">Anrede</label>
            </div>

            <div class="d-flex">
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="vorname" name="vorname" placeholder="Vorname" required />
                <label for="vorname">Vorname</label>
              </div>
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="nachname" name="nachname" placeholder="Nachname" required />
                <label for="nachname">Nachname</label>
              </div>
            </div>

            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="floatingAdress" name="adresse" placeholder="Adresse" required />
              <label for="floatingAdress">Adresse</label>
            </div>

            <div class="d-flex">
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="floatingPlz" name="plz" placeholder="PLZ" required />
                <label for="floatingPlz">PLZ</label>
              </div>
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="floatingOrt" name="ort" placeholder="Ort" required />
                <label for="floatingOrt">Ort</label>
              </div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="form-floating mb-3">
              <input
                type="email"
                class="form-control"
                id="floatingEmail"
                name="email"
                placeholder="name@example.com"
                required
              />
              <label for="floatingEmail">Email-Adresse</label>
            </div>

            <div class="form-floating mb-3">
              <input
                type="text"
                class="form-control"
                id="floatingUsername"
                name="username"
                placeholder="Username"
                required
              />
              <label for="floatingUsername">Benutzername</label>
            </div>

            <div class="form-floating mb-3">
              <input
                type="password"
                class="form-control"
                id="floatingPassword"
                name="password"
                placeholder="Passwort"
                required
              />
              <label for="floatingPassword">Passwort</label>
            </div>

            <div class="form-floating mb-3">
              <input
                type="password"
                class="form-control"
                id="floatingPassword2"
                name="password2"
                placeholder="Passwort wiederholen"
                required
              />
              <label for="floatingPassword2">Passwort wiederholen</label>
            </div>
          </div>
        </div>

        <div class="text-center">
          <button type="submit" class="btn btn-primary" name="reg-submit">Registrieren</button>
        </div>
      </form>
    </div>

    <div class="container mt-3 mb-2 text-center">
      Sie haben bereits ein Konto? <router-link to="/login">Hier anmelden</router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Registration form data goes here
    };
  },
  methods: {
    submitRegistration() {
      // Handle registration form submission logic here
    }
  }
};
</script>

<style scoped>
body {
  background-color: ghostwhite;
}

.form-container {
  max-width: 360px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 10px;
}

.register-container {
  max-width: 1000px;
}

.form-floating .form-control {
  height: 58px;
  padding: 1rem .75rem;
}

.d-flex .form-floating {
  flex: 1;
}

.d-flex .form-floating + .form-floating {
  margin-left: 10px;
}

.btn-primary {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.btn-primary:hover {
  transform: scale(1.01);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.container {
  margin-top: 20px;
  text-align: center;
}
</style>
