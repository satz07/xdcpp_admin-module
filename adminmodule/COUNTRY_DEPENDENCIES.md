# Country Dependencies - What to Configure When Adding a New Country

When adding a new country to the system, you need to configure the following dependent entities:

## 1. **Core Country Information** (Required)
   - **Country Name** (mandatory)
   - **Country Code** (mandatory, 2-letter code, e.g., "US", "IN")
   - **ISO Code** (3-letter ISO code, e.g., "USA", "IND")
   - **Dialing Code** (phone country code, e.g., 1, 91)
   - **Default Language** (optional)
   - **Is Enabled** (boolean flag)
   - **Is Restricted** (boolean flag, default: false)

## 2. **Currency Configuration** (Required)
   - **Currencies** - Create currency entries for the country
     - Currency Code (e.g., "USD", "INR")
     - Currency Name
     - Currency Description
     - Link currency to country via `countryCode` field
   - **Country-Currency Mapping** (`admin_country_curreny_mapping`)
     - Map the country to its currency/currencies
     - Required for transaction processing

## 3. **Payment Receive Modes** (Required for Transactions)
   - **PaymentReceiveMode** entries for the country
     - Links to: Country, Currency, PaymentCodeMaster
     - Defines how money can be received in that country
     - Required fields:
       - User Type (INDIVIDUAL, BUSINESS, etc.)
       - User Category
       - Country (reference to Countries)
       - Currency (reference to Currencies)
       - Payment Code Master
       - Payment Mode Type (SEND, RECEIVE)
       - Description, Comments, Remarks

## 4. **Fee Configuration** (Required for Transactions)
   - **FeeMaster** entries
     - Send Country Code (reference to Countries)
     - Receive Country Code (reference to Countries)
     - Send Currency Code
     - Receive Currency Code
     - Payment Code & Receive Code (PaymentReceiveMode)
     - Transaction fee ranges and amounts
     - User Type and User Category

## 5. **TAT (Turn Around Time) Configuration** (Required)
   - **TAT** entries
     - Originating Country Currency (Currencies)
     - Disbursement Country Currency (Currencies)
     - Payment Mode & Receive Mode (PaymentReceiveMode)
     - Payment Hours & Receive Hours
     - Expiry Time & Cancel Time
     - Partner Name

## 6. **Global Limits** (Required for Transaction Limits)
   - **GlobalLimitMaster** entries
     - Send Country Code & Receive Country Code
     - Send Currency Code & Receive Currency Code
     - Payment Code & Receive Code
     - Transaction limits:
       - Min/Max Transaction Limits
       - Daily, Weekly, Monthly, Quarterly, Half-Yearly limits
       - Daily/Weekly/Monthly/Quarterly/Half-Yearly transaction count limits

## 7. **Margin Master** (If applicable)
   - **MarginMaster** (extends FeeSettingBaseModel)
     - Send/Receive Country Codes
     - Send/Receive Currency Codes
     - Margin rates and configurations

## 8. **Service Charge Master** (If applicable)
   - **ServiceChargeMaster** (extends FeeSettingBaseModel)
     - Send/Receive Country Codes
     - Send/Receive Currency Codes
     - Service charge configurations

## 9. **Country Holidays** (Optional but Recommended)
   - **CountryHolidays** entries
     - Map country to holiday calendar
     - Required for transaction scheduling and TAT calculations
     - Links: Country ID → Holiday ID

## 10. **Delivery Partner Master** (If applicable)
   - **DeliveryPartnerMaster** entries
     - Operating Country (reference to Countries)
     - Payment Mode (PaymentReceiveMode)
     - Partner location, limits, and codes

## 11. **Location Master** (If using location-based features)
   - **LocationMaster** entries
     - Location Name (can be country name or region)
     - Used by Products, Partners, Groups, Users

## Summary Checklist for Adding a New Country:

### Minimum Required:
- [ ] Create Country entry with all basic fields
- [ ] Create at least one Currency for the country
- [ ] Create Country-Currency Mapping
- [ ] Create at least one PaymentReceiveMode (RECEIVE type)
- [ ] Create FeeMaster entries for send/receive combinations
- [ ] Create TAT entries for the country
- [ ] Create GlobalLimitMaster entries

### Recommended:
- [ ] Create Country Holidays
- [ ] Create LocationMaster entry
- [ ] Configure Margin Master (if applicable)
- [ ] Configure Service Charge Master (if applicable)
- [ ] Set up Delivery Partner Master (if applicable)

### Optional:
- [ ] Configure multiple currencies if country uses multiple
- [ ] Set up multiple payment receive modes
- [ ] Configure country-specific restrictions

## Database Tables Involved:
1. `admin_countries` - Main country table
2. `admin_currencies` - Currency table (linked via country_code)
3. `admin_country_curreny_mapping` - Country-Currency mapping
4. `payment_recevie_mode_master` - Payment receive modes
5. `fee_master` - Fee configurations
6. `admin_tat` - Turn around time
7. `global_limit_master` - Transaction limits
8. `margin_master` - Margin configurations (if used)
9. `service_charge_master` - Service charge (if used)
10. `admin_country_holidays` - Holiday calendar
11. `delivery_partner_master` - Delivery partners
12. `admin_location_master` - Location master

## Notes:
- All country-dependent entities use foreign key relationships
- Countries are referenced in transaction processing (`UserCalculatorMapping`)
- Countries are used in compliance reporting and user registration
- Country codes must be unique and uppercase
- ISO codes should follow ISO 3166-1 alpha-3 standard

## Country Deletion

### How to Delete a Country

When attempting to delete a country, the system now performs a comprehensive check of all dependencies. The deletion will be blocked if the country is mapped to any of the following:

1. **Payment Receive Modes** - Payment methods configured for the country
2. **Currencies** - Currencies linked to the country
3. **Country-Currency Mappings** - Explicit currency mappings
4. **Fee Masters** - Fee configurations where country is used as send or receive country
5. **Global Limit Masters** - Transaction limits configured for the country
6. **Margin Masters** - Margin configurations for the country
7. **Service Charge Masters** - Service charge configurations for the country
8. **Country Holidays** - Holiday calendar entries for the country
9. **Delivery Partner Masters** - Delivery partners operating in the country

### Error Messages

When deletion is blocked, you will see a detailed error message indicating which dependencies exist, for example:
- "Cannot delete country. It is mapped to: Payment Receive Modes (3), Currencies (2), Fee Masters (5)"

### Before Deleting a Country

To successfully delete a country, you must first:
1. Delete or unmap all Payment Receive Modes for the country
2. Delete all Currencies linked to the country
3. Delete all Country-Currency Mappings
4. Delete all Fee Masters, Global Limit Masters, Margin Masters, and Service Charge Masters that reference the country
5. Delete all Country Holidays for the country
6. Delete or update all Delivery Partner Masters operating in the country

**Note:** The system performs a soft delete (sets `isDeleted = true`) rather than physically removing the record from the database.
