<template>
  <div class="help">
    <h1>Help & Support</h1>

    <section class="faq-section">
      <h2>Frequently Asked Questions</h2>
      <div class="faq">
        <div class="faq-item">
          <h3>How do I create an account?</h3>
          <p>
            Click the "Register" button at the top-right corner of the homepage. Fill in the required information and
            submit the form to create your account.
          </p>
        </div>

        <div class="faq-item">
          <h3>How do I reset my password?</h3>
          <p>
            If you’ve forgotten your password, click the "Forgot Password?" link on the login page and follow the
            instructions to reset it.
          </p>
        </div>

        <div class="faq-item">
          <h3>How can I track my order?</h3>
          <p>
            After logging in, go to "Order History" under your account dashboard to see the status of your orders.
          </p>
        </div>

        <div class="faq-item">
          <h3>How can I contact customer support?</h3>
          <p>
            You can reach our customer support team via email at <a
              href="mailto:support@company.com">support@company.com</a> or by phone at +43 123 456 789.
          </p>
        </div>
      </div>
    </section>

    <section class="contact-section">
      <h2>Need Further Assistance?</h2>
      <p>
        If you need additional help, feel free to <router-link to="/contact">contact us</router-link>. Our support team
        is available to assist you with any questions or issues.
      </p>
    </section>
  </div>
</template>

<style scoped>
.help {
  padding: 20px;
  max-width: 900px;
  margin: 0 auto;
}

h1 {
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 30px;
}

.faq-section,
.contact-section {
  margin-bottom: 30px;
}

.faq-item {
  margin-bottom: 20px;
}

.faq-item h3 {
  font-size: 1.25rem;
  margin-bottom: 10px;
}

.faq-item p {
  font-size: 1rem;
  line-height: 1.6;
}

.contact-section h2 {
  font-size: 1.75rem;
  margin-bottom: 15px;
}

.contact-section p {
  font-size: 1.1rem;
}
</style>