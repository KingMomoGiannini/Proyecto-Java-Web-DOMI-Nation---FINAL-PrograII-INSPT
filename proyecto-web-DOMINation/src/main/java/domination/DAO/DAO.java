/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package domination.DAO;

import java.util.List;

/**
 *
 * @author giann
 */
public interface DAO<T, K> {
   List<T> getAll() throws Exception;
   void create(T elObjeto)throws Exception;
   void update(T elObjeto)throws Exception;
   void delete(K elId)throws Exception;
   T getByID(K elId) throws Exception;

}
