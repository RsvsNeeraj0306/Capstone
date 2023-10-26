import React, { useState } from 'react';

const SoftwareChangePlanForm = ({ onChangePlan }) => {
  const [softwareId, setSoftwareId] = useState('');
  const [licenseKey, setLicenseKey] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [purchaseDate, setPurchaseDate] = useState('');
  const [typeOfPlan, setTypeOfPlan] = useState('');
  const [quantity, setQuantity] = useState('');
  const [priceOfSoftware, setPriceOfSoftware] = useState('');
  const [message, setMessage] = useState('');

  const resetForm = () => {
    setSoftwareId('');
    setLicenseKey('');
    setExpiryDate('');
    setPurchaseDate('');
    setTypeOfPlan('');
    setQuantity('');
    setPriceOfSoftware('');
    setMessage('');
  };

  const handlePlanChangeSubmit = async (e) => {
    e.preventDefault();

    const planChangeData = {
      software: {
        id: softwareId,
        licenseKey,
        expiryDate,
        purchaseDate,
        typeOfPlan,
        quantity,
        priceOfSoftware,
      },
    };

    try {
      const response = await fetch('http://localhost:8080/api/changePlan', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(planChangeData),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage('Plan changed successfully');
        onChangePlan(data); // Notify the parent component about the successful plan change
      } else {
        setMessage('Failed to change plan. Please check the software ID.');
      }
    } catch (error) {
      setMessage('An error occurred while changing the plan.');
    }

    resetForm();
  };

  return (
    <div className="change-plan-form">
      <h2>Change Plan</h2>
      <form onSubmit={handlePlanChangeSubmit}>
        <label>
          Software ID:
          <input
            type="text"
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          />
        </label>
        <label>
          License Key:
          <input
            type="text"
            value={licenseKey}
            onChange={(e) => setLicenseKey(e.target.value)}
          />
        </label>
        <label>
          Expiry Date:
          <input
            type="date"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
          />
        </label>
        <label>
          Purchase Date:
          <input
            type="date"
            value={purchaseDate}
            onChange={(e) => setPurchaseDate(e.target.value)}
          />
        </label>
        <label>
          Type of Plan:
          <input
            type="text"
            value={typeOfPlan}
            onChange={(e) => setTypeOfPlan(e.target.value)}
          />
        </label>
        <label>
          Quantity:
          <input
            type="text"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
          />
        </label>
        <label>
          Price of Software:
          <input
            type="text"
            value={priceOfSoftware}
            onChange={(e) => setPriceOfSoftware(e.target.value)}
          />
        </label>
        <button type="submit">Change Plan</button>
      </form>
      <div className="message">{message}</div>
    </div>
  );
};

export default SoftwareChangePlanForm;
