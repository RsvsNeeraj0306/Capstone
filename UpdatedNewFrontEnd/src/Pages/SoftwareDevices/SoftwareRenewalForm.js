import React, { useState, useEffect } from 'react';

const SoftwareRenewalForm = () => {
  const [softwareId, setSoftwareId] = useState('');
  const [purchaseDate, setPurchaseDate] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [licenseKey, setLicenseKey] = useState('');
  const [message, setMessage] = useState('');
  const [softwareList, setSoftwareList] = useState([]);
  const [newPlan, setNewPlan] = useState('');            // New plan field
  const [newPrice, setNewPrice] = useState('');          // New price field
  const [newUsers, setNewUsers] = useState('');          // New users field
  const [newLicenseKey, setNewLicenseKey] = useState(''); // New license key field
  const [newPurchaseDate, setNewPurchaseDate] = useState(''); // New purchase date field
  const [newExpiryDate, setNewExpiryDate] = useState(''); // New expiry date field
  const [renewFormVisible, setRenewFormVisible] = useState(true); // To control which form is visible


  const resetForm = () => {
    setSoftwareId(''); // Reset software ID
    setPurchaseDate(''); // Reset purchase date
    setExpiryDate(''); // Reset expiry date
    setLicenseKey(''); // Reset license key
    setNewPlan(''); // Reset new plan
    setNewPrice(''); // Reset new price
    setNewUsers(''); // Reset new users
    setNewLicenseKey(''); // Reset new license key
    setNewPurchaseDate(''); // Reset new purchase date
    setNewExpiryDate(''); // Reset new expiry date
    setMessage(''); // Reset the message
  };


  
  useEffect(() => {
    // Fetch the list of existing software from your API
    fetch('http://localhost:8080/api/allSoftware')
      .then((response) => response.json())
      .then((data) => {
        setSoftwareList(data);
      })
      .catch((error) => console.error('Error fetching software: ', error));
  }, []);

  const handleRenew = () => {
    setRenewFormVisible(true);
  };

  const handleChangePlan = () => {
    setRenewFormVisible(false);
  };

  const handleRenewSubmit = async (e) => {
    e.preventDefault();
    // Renew software logic
    const renewalData = {
      software: {
        id: softwareId,
        purchaseDate,
        expiryDate,
        licenseKey,
      },

      
    };


    try {
      const response = await fetch('http://localhost:8080/api/renewSoftware', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(renewalData),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage(data.responseBody);
      } else {
        setMessage('Failed to renew software. Please check the software ID.');
      }
    } catch (error) {
      setMessage('An error occurred while renewing the software.');
    }
    resetForm();
  };

  const handleChangePlanSubmit = async (e) => {
    e.preventDefault();
    // Change plan logic
    const changePlanData = {
      software: {
        id: softwareId,
        typeOfPlan: newPlan,
        priceOfSoftware: newPrice,
        usersCanUse: newUsers,
        licenseKey: newLicenseKey,         // New license key
        purchaseDate: newPurchaseDate,     // New purchase date
        expiryDate: newExpiryDate, 
        
      },
    };

    try {
      const response = await fetch('http://localhost:8080/api/changePlan', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(changePlanData),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage(data.responseBody);
      } else {
        setMessage('Failed to change plan. Please check the software ID and new plan details.');
      }
    } catch (error) {
      setMessage('An error occurred while changing the plan.');
    }

    resetForm();
  };

  return (
    <div className="form-container">
      <h2 className="form-label">Software Management</h2>
      <button onClick={handleRenew}>Renew</button>
      <button onClick={handleChangePlan}>Change Plan</button>
      {renewFormVisible ? (
        // Renew Software Form
        <form onSubmit={handleRenewSubmit}>
          <label className="form-label">
            Select Software by Name:
            <select value={softwareId} onChange={(e) => setSoftwareId(e.target.value)}>
              <option value="">Select Software</option>
              {softwareList.map((software) => (
                <option key={software.id} value={software.id}>
                  {software.softwareName}
                </option>
              ))}
            </select>
          </label>
          <label className="form-label">
            OR Select Software by ID:
            <input type="text" value={softwareId} onChange={(e) => setSoftwareId(e.target.value)} />
          </label>
          <label className="form-label">
            Purchase Date:
            <input type="date" value={purchaseDate} onChange={(e) => setPurchaseDate(e.target.value)} />
          </label>
          <label className="form-label">
            Expiry Date:
            <input type="date" value={expiryDate} onChange={(e) => setExpiryDate(e.target.value)} />
          </label>
          <label className="form-label">
            License Key:
            <input type="text" value={licenseKey} onChange={(e) => setLicenseKey(e.target.value)} />
          </label>
          <button type="submit">Renew Software</button>
        </form>
      ) : (
        // Change Plan Form
        <form onSubmit={handleChangePlanSubmit}>
        <label className="form-label">
          Select Software by Name:
          <select value={softwareId} onChange={(e) => setSoftwareId(e.target.value)}>
            <option value="">Select Software</option>
            {softwareList.map((software) => (
              <option key={software.id} value={software.id}>
                {software.softwareName}
              </option>
            ))}
          </select>
        </label>
        <label className="form-label">
          OR Select Software by ID:
          <input type="text" value={softwareId} onChange={(e) => setSoftwareId(e.target.value)} />
        </label>
        <label className="form-label">
          New Plan:
          <input type="text" value={newPlan} onChange={(e) => setNewPlan(e.target.value)} />
        </label>
        <label className="form-label">
          New Price:
          <input type="text" value={newPrice} onChange={(e) => setNewPrice(e.target.value)} />
        </label>
        <label className="form-label">
          New Users:
          <input type="text" value={newUsers} onChange={(e) => setNewUsers(e.target.value)} />
        </label>
        <label className="form-label">
          New License Key:
          <input type="text" value={newLicenseKey} onChange={(e) => setNewLicenseKey(e.target.value)} />
        </label>
        <label className="form-label">
          New Purchase Date:
          <input type="date" value={newPurchaseDate} onChange={(e) => setNewPurchaseDate(e.target.value)} />
        </label>
        <label className="form-label">
          New Expiry Date:
          <input type="date" value={newExpiryDate} onChange={(e) => setNewExpiryDate(e.target.value)} />
        </label>
        <button type="submit">Change Plan</button>
      </form>      
      )}
      <div>{message}</div>
    </div>
  );
};

export default SoftwareRenewalForm;