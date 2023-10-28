import React, { useEffect, useState } from 'react';
import './RenewSoftwareForm.css'; // Import the RenewSoftwareForm.css file
import { toast } from 'react-toastify';

const SoftwareChangePlanForm = ({ onChangePlan }) => {
  const [softwareId, setSoftwareId] = useState('');
  const [licenseKey, setLicenseKey] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [purchaseDate, setPurchaseDate] = useState('');
  const [typeOfPlan, setTypeOfPlan] = useState('');
  const [quantity, setQuantity] = useState('');
  const [priceOfSoftware, setPriceOfSoftware] = useState('');
  const [message, setMessage] = useState('');

  const [software, setSoftware] = useState([]);

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
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(planChangeData),
      });

      if (response.ok) {
        const data = await response.json();
        toast.success('Plan changed successfully');
      } 
      else {
        toast.error('Failed to change plan. Please check the software ID.');
      }
    } catch (error) {
      toast.error('An error occurred while changing the plan.');
    }

    resetForm();
  };

  useEffect(() => {
    const fetchSoftwareDevices = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/allSoftware', {
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
          },
        });
        const data = await response.json();
        setSoftware(data);
      } catch (error) {
        console.error('Error:', error);
      }
    };

    fetchSoftwareDevices();
  }, []);

  const softwareOptions = software.map((software) => (
    <option key={software.id} value={software.id}>
      {software.id}
    </option>
  ));

  return (
    <div className="form-container">
      <h2>Change Plan</h2>
      <form onSubmit={handlePlanChangeSubmit}>
        <label>
          Software ID:
          <select
            value={softwareId}
            onChange={(e) => setSoftwareId(e.target.value)}
          >
            <option value="">Select a software</option>
            {softwareOptions}
          </select>
        </label>
        <br />
        <br />
        <label>
          License Key:
          <input
            type="text"
            className="custom-textfield"
            value={licenseKey}
            onChange={(e) => setLicenseKey(e.target.value)}
          />
        </label>
        <label>
          Expiry Date:
          <input
            type="date"
            className="custom-textfield"
            value={expiryDate}
            onChange={(e) => setExpiryDate(e.target.value)}
          />
        </label>
        <label>
          Purchase Date:
          <input
            type="date"
            className="custom-textfield"
            value={purchaseDate}
            onChange={(e) => setPurchaseDate(e.target.value)}
          />
        </label>
        <label>
          Type of Plan:
          <select
            value={typeOfPlan}
            onChange={(e) => setTypeOfPlan(e.target.value)}
            className="custom-textfield"
          >
            <option value="">Select a plan</option>
            <option value="Free Plan">Free Plan</option>
            <option value="Basic Plan">Basic Plan</option>
            <option value="Pro Plan">Pro Plan</option>
            <option value="Premium Plan">Premium Plan</option>
            <option value="Enterprise Plan">Enterprise Plan</option>
          </select>
        </label>
        <label>
          Quantity:
          <input
            type="text"
            className="custom-textfield"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
          />
        </label>
        <label>
          Price of Software:
          <input
            type="text"
            className="custom-textfield"
            value={priceOfSoftware}
            onChange={(e) => setPriceOfSoftware(e.target.value)}
          />
        </label>
        <button type="submit" className="custom-button">
          Change Plan
        </button>
      </form>
      <div className="message">{message}</div>
    </div>
  );
};

export default SoftwareChangePlanForm;
