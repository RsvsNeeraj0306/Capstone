import React, { useState, useEffect } from 'react';

const SoftwareForm = () => {
  const [softwareData, setSoftwareData] = useState({
    manufacturer: {
      fieldOfWork: 'Software', // Set the default value to 'Software'
    },
    softwareName: '',
    purchaseDate: '',
    expiryDate: '',
    typeOfPlan: '',
    usersCanUse: '',
    priceOfSoftware: '',
  });

  const [manufacturers, setManufacturers] = useState([]);
  const [responseMessage, setResponseMessage] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/api/allManufacture')
      .then((response) => response.json())
      .then((data) => setManufacturers(data))
      .catch((error) => console.error('Error fetching manufacturers: ', error));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSoftwareData({
      ...softwareData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const softwareDTO = {
      software: {
        softwareName: softwareData.softwareName,
        purchaseDate: softwareData.purchaseDate,
        expiryDate: softwareData.expiryDate,
        typeOfPlan: softwareData.typeOfPlan,
        usersCanUse: softwareData.usersCanUse,
        priceOfSoftware: softwareData.priceOfSoftware,
      },
      manufacturer: {
        name: softwareData.manufacturer.name,
        fieldOfWork: softwareData.manufacturer.fieldOfWork,
      },
    };

    fetch('http://localhost:8080/api/addSoftware', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(softwareDTO),
    })
      .then((response) => response.json())
      .then((data) => setResponseMessage(data.responseBody))
      .catch((error) => console.error('Error adding software: ', error));
  };

  return (
    <div>
      <h2>Add Software</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Manufacturer:
          <select
            name="manufacturer.name"
            value={softwareData.manufacturer.name}
            onChange={handleChange}
          >
            <option value="">Select a manufacturer</option>
            {manufacturers.map((manufacturer) => (
              <option key={manufacturer.id} value={manufacturer.name}>
                {manufacturer.name}
              </option>
            ))}
          </select>
        </label>
        <label>
          Field of Work:
          <input
            type="text"
            name="manufacturer.fieldOfWork"
            value={softwareData.manufacturer.fieldOfWork}
            onChange={handleChange}
            readOnly
          />
        </label>
        <label>
          Software Name:
          <input
            type="text"
            name="softwareName"
            value={softwareData.softwareName}
            onChange={handleChange}
          />
        </label>
        <label>
          Purchase Date:
          <input
            type="date"
            name="purchaseDate"
            value={softwareData.purchaseDate}
            onChange={handleChange}
          />
        </label>
        <label>
          Expiry Date:
          <input
            type="date"
            name="expiryDate"
            value={softwareData.expiryDate}
            onChange={handleChange}
          />
        </label>
        <label>
          Type of Plan:
          <input
            type="text"
            name="typeOfPlan"
            value={softwareData.typeOfPlan}
            onChange={handleChange}
          />
        </label>
        <label>
          Users Can Use:
          <input
            type="number"
            name="usersCanUse"
            value={softwareData.usersCanUse}
            onChange={handleChange}
          />
        </label>
        <label>
          Price of Software:
          <input
            type="number"
            name="priceOfSoftware"
            value={softwareData.priceOfSoftware}
            onChange={handleChange}
          />
        </label>
        <button type="submit">Add Software</button>
      </form>
      <div>{responseMessage}</div>
    </div>
  );
};

export default SoftwareForm;